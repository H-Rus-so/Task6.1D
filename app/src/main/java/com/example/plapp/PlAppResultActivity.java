package com.example.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class PlAppResultActivity extends AppCompatActivity {

    LinearLayout resultContainer;
    MaterialButton newQuizButton, finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plapp_result);

        // Find the views on the screen
        resultContainer = findViewById(R.id.resultContainer);
        newQuizButton = findViewById(R.id.newQuizButton);
        finishButton = findViewById(R.id.finishButton);

        // Get the list of feedback strings from the quiz results
        ArrayList<String> feedbackList = getIntent().getStringArrayListExtra("feedback_list");

        // If the list exists and isn't empty, show each feedback message in a card
        if (feedbackList != null && !feedbackList.isEmpty()) {
            for (String feedback : feedbackList) {
                // Create a CardView for each feedback item
                CardView card = new CardView(this);
                card.setCardElevation(4f);
                card.setRadius(12f);
                card.setUseCompatPadding(true);

                // Set margins for the card
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(0, 16, 0, 0);
                card.setLayoutParams(cardParams);

                // Create a layout to hold the feedback text
                LinearLayout innerLayout = new LinearLayout(this);
                innerLayout.setOrientation(LinearLayout.VERTICAL);
                innerLayout.setPadding(24, 24, 24, 24);

                // Create a TextView to display the feedback
                TextView tv = new TextView(this);
                tv.setText(feedback);
                tv.setTextColor(getResources().getColor(android.R.color.black));
                tv.setTextSize(16f);

                // Add the text to the layout, then layout into the card
                innerLayout.addView(tv);
                card.addView(innerLayout);
                resultContainer.addView(card); // Finally add the card to the screen
            }
        }

        // When the "Try Another Quiz" button is clicked, go back to the dashboard
        newQuizButton.setOnClickListener(v -> {
            Intent i = new Intent(this, PlAppDashboardActivity.class);
            startActivity(i);
        });

        // When "Finish" is clicked, close the app completely
        finishButton.setOnClickListener(v -> finishAffinity());
    }
}
