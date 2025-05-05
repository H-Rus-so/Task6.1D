package com.example.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

// This screen lets users sign up by entering their info, then it takes them to the interest selection screen.
public class PlAppRegisterActivity extends AppCompatActivity {
    private EditText etRegUser, etRegEmail, etRegConfirmEmail,
            etRegPass, etRegConfirmPass, etRegPhone;
    private Button btnCreateAccountPlApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plapp_register);

        // Connect all the input fields and the button using their IDs
        etRegUser          = findViewById(R.id.etRegUser);
        etRegEmail         = findViewById(R.id.etRegEmail);
        etRegConfirmEmail  = findViewById(R.id.etRegConfirmEmail);
        etRegPass          = findViewById(R.id.etRegPass);
        etRegConfirmPass   = findViewById(R.id.etRegConfirmPass);
        etRegPhone         = findViewById(R.id.etRegPhone);
        btnCreateAccountPlApp = findViewById(R.id.btnCreateAccountPlApp);

        // When the "Create Account" button is clicked
        btnCreateAccountPlApp.setOnClickListener(v -> {
            // Check that the required fields aren't empty
            if (etRegUser.getText().toString().trim().isEmpty() ||
                    etRegEmail.getText().toString().trim().isEmpty() ||
                    etRegPass.getText().toString().trim().isEmpty() ||
                    etRegConfirmPass.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Make sure both password fields match
            if (!etRegPass.getText().toString().equals(
                    etRegConfirmPass.getText().toString())) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // If everything checks out, go to the interest selection screen
            Intent i = new Intent(this, PlAppInterestActivity.class);
            i.putExtra("username", etRegUser.getText().toString().trim()); // pass the username to the next screen
            startActivity(i);
            finish(); // Close the current screen
        });
    }
}
