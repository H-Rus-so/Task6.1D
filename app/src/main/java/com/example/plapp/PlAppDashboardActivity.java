package com.example.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.SwitchCompat;

public class PlAppDashboardActivity extends AppCompatActivity {

    private TextView     tvHello, tvDue;
    private CardView     cardTask1;
    private SwitchCompat switchPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plapp_dashboard);

        // Bind using the XML IDs
        tvHello       = findViewById(R.id.tvHello);
        tvDue         = findViewById(R.id.tvDue);
        cardTask1     = findViewById(R.id.cardTask1);
        switchPreview = findViewById(R.id.switchPreview);

        // Get the student's name from the intent
        String name = getIntent().getStringExtra("name");
        if (name == null) name = "Student";

        // Populate the header
        tvHello.setText("Hello, " + name + "!");
        tvDue.setText("You have 1 task due");

        // Preview toggle listener
        switchPreview.setOnCheckedChangeListener((btn, isChecked) -> {
            Toast.makeText(this,
                    isChecked ? "Preview mode ON" : "Preview mode OFF",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // Clicking the task card launches the quiz
        String finalName = name;
        cardTask1.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlAppQuizActivity.class);
            intent.putExtra("name", finalName);
            startActivity(intent);
        });
    }
}
