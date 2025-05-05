package com.example.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PlAppQuizActivity extends AppCompatActivity {
    private static final String TAG = "PlAppQuiz";

    // These are the views we'll use for showing quiz questions and answers
    private TextView tvTaskTitle, tvTaskDesc, tvQuestionNumber, tvQuestionText;
    private CardView cardQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;
    private MaterialButton btnSubmit;

    // List of all quiz questions and current state tracking
    private final List<QuizQuestion> quizList = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plapp_quiz);

        // Step 1: Connect all our views using their IDs
        tvTaskTitle      = findViewById(R.id.tvTaskTitle);
        tvTaskDesc       = findViewById(R.id.tvTaskDesc);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestionText   = findViewById(R.id.tvQuestionText);
        cardQuestion     = findViewById(R.id.cardQuestion);
        rgOptions        = findViewById(R.id.rgOptions);
        rbOption1        = findViewById(R.id.rbOption1);
        rbOption2        = findViewById(R.id.rbOption2);
        rbOption3        = findViewById(R.id.rbOption3);
        btnSubmit        = findViewById(R.id.btnSubmit);

        // Step 2: Start fetching quiz data from the Flask backend
        fetchQuizData();
    }

    private void fetchQuizData() {
        // Get the topic from the previous screen (or use "General" as the default)
        String topic = getIntent().getStringExtra("topic");
        if (topic == null || topic.isEmpty()) {
            topic = "General";
        }

        // Safely encode the topic for use in a URL
        String encodedTopic;
        try {
            encodedTopic = URLEncoder.encode(topic, "UTF-8");
        } catch (Exception e) {
            // Fallback in case something goes wrong
            encodedTopic = topic.replace(" ", "%20");
        }

        // Create the request URL (using emulator's special IP)
        String url = "http://172.29.192.1:5000/getQuiz?topic=" + encodedTopic;
        Log.i(TAG, "Fetching URL: " + url);

        // Set up the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parse the JSON response into QuizQuestion objects
                        JSONArray arr = response.getJSONArray("quiz");
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            String question = obj.getString("question");
                            JSONArray opts = obj.getJSONArray("options");

                            // Store the options into a list
                            List<String> list = new ArrayList<>();
                            for (int j = 0; j < opts.length(); j++) {
                                list.add(opts.getString(j));
                            }

                            String correct = obj.getString("correct_answer");
                            quizList.add(new QuizQuestion(question, list, correct));
                        }

                        // Show the first question once data is ready
                        showQuestion(0);

                    } catch (Exception e) {
                        Log.e(TAG, "Parse error", e);
                        Toast.makeText(this,
                                R.string.select_answer_prompt,
                                Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Volley error", error);
                    Toast.makeText(this,
                            R.string.select_answer_prompt,
                            Toast.LENGTH_LONG).show();
                }
        );

        // Increase timeout in case the API is slow
        req.setRetryPolicy(new DefaultRetryPolicy(
                60_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add the request to the queue to run it
        queue.add(req);
    }

    private void showQuestion(int index) {
        currentIndex = index;
        QuizQuestion q = quizList.get(index);
        int total = quizList.size();

        // Step 3: Reset the UI before showing a new question
        cardQuestion.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.card_bg)
        );
        rgOptions.clearCheck();
        rbOption1.setEnabled(true);
        rbOption2.setEnabled(true);
        rbOption3.setEnabled(true);
        btnSubmit.setText(R.string.submit);

        // Step 4: Fill in all the data for the current question
        tvTaskTitle.setText(R.string.task_title);
        tvTaskDesc.setText(R.string.task_desc);
        tvQuestionNumber.setText(
                getString(R.string.question_counter, index + 1, total)
        );
        tvQuestionText.setText(q.question);
        rbOption1.setText(q.options.get(0));
        rbOption2.setText(q.options.get(1));
        rbOption3.setText(q.options.get(2));

        // Step 5: Set up what happens when user taps the Submit/Next/Finish button
        btnSubmit.setOnClickListener(v -> {
            // If we're in the "Submit" phase
            if (btnSubmit.getText().equals(getString(R.string.submit))) {
                int selId = rgOptions.getCheckedRadioButtonId();
                if (selId == -1) {
                    // User didn’t pick an answer yet
                    Toast.makeText(this,
                            R.string.select_answer_prompt,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected answer
                RadioButton chosen = findViewById(selId);
                String answer = chosen.getText().toString();
                boolean correct = answer.equalsIgnoreCase(q.correctAnswer);
                if (correct) score++;

                // Change background color to green (correct) or red (wrong)
                int color = ContextCompat.getColor(
                        this,
                        correct
                                ? R.color.result_correct
                                : R.color.result_incorrect
                );
                cardQuestion.setCardBackgroundColor(color);

                // Disable changing answers after submitting
                rbOption1.setEnabled(false);
                rbOption2.setEnabled(false);
                rbOption3.setEnabled(false);

                // Change button text to "Next" or "Finish"
                btnSubmit.setText(
                        index + 1 < total
                                ? R.string.next
                                : R.string.finish
                );

            } else {
                // We're in the "Next" or "Finish" phase
                if (index + 1 < total) {
                    // Show the next question
                    showQuestion(index + 1);
                } else {
                    // Quiz is done — go to the results screen
                    Intent i = new Intent(this, PlAppResultActivity.class);
                    i.putExtra("name", getIntent().getStringExtra("name"));
                    i.putExtra("score", score);
                    i.putExtra("total", total);
                    startActivity(i);
                    finish(); // Don’t let user go back to quiz screen
                }
            }
        });
    }
}
