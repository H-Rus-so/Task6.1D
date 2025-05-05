package com.example.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PlAppLoginActivity extends AppCompatActivity {
    private TextInputEditText etUsername, etPassword;
    private MaterialButton    btnLogin;
    private TextView          tvNeedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plapp_login);

        // Find the input fields and buttons from the layout using their IDs
        etUsername     = findViewById(R.id.etUsername);
        etPassword     = findViewById(R.id.etPassword);
        btnLogin       = findViewById(R.id.btnLogin);
        tvNeedAccount  = findViewById(R.id.tvNeedAccount);

        // Set what happens when the login button is tapped
        btnLogin.setOnClickListener(v -> {
            // Get the text the user typed in
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            // Check if either field is empty
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter both username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Right now, this just checks against a dummy test user ("test" and "1234")
            // Later this should be replaced with real authentication
            if (user.equalsIgnoreCase("test") && pass.equals("1234")) {
                // If the login is successful, send the user to the next screen
                Intent i = new Intent(this, PlAppInterestActivity.class);
                i.putExtra("name", user); // Pass the username to the next activity
                startActivity(i);
                finish(); // Close this screen so user can't come back to it with the back button
            } else {
                // If the login info doesn't match, show an error message
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // If the user taps the "Need an Account?" text, take them to the register screen
        tvNeedAccount.setOnClickListener(v -> {
            Intent i = new Intent(this, PlAppRegisterActivity.class);
            startActivity(i);
        });
    }
}
