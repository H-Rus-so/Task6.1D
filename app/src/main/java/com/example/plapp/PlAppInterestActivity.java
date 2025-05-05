package com.example.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;

public class PlAppInterestActivity extends AppCompatActivity {
    private Chip                 chipAlgorithms,
            chipDataStructures,
            chipWebDev,
            chipTesting;
    private MaterialButton       btnNextInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plapp_interest);

        // Bind chips & button
        chipAlgorithms     = findViewById(R.id.chipAlgorithms);
        chipDataStructures = findViewById(R.id.chipDataStructures);
        chipWebDev         = findViewById(R.id.chipWebDev);
        chipTesting        = findViewById(R.id.chipTesting);
        btnNextInterest    = findViewById(R.id.btnNextInterest);

        btnNextInterest.setOnClickListener(v -> {
            ArrayList<String> selected = new ArrayList<>();
            if (chipAlgorithms.isChecked())     selected.add("Algorithms");
            if (chipDataStructures.isChecked()) selected.add("Data Structures");
            if (chipWebDev.isChecked())         selected.add("Web Development");
            if (chipTesting.isChecked())        selected.add("Testing");

            if (selected.isEmpty()) {
                Toast.makeText(this, "Select at least one interest", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass name & interests to Dashboard
            String name = getIntent().getStringExtra("name");
            Intent intent = new Intent(this, PlAppDashboardActivity.class);
            intent.putExtra("name", name);
            intent.putStringArrayListExtra("interests", selected);
            startActivity(intent);
            finish();
        });
    }
}
