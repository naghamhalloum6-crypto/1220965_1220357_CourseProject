package com.example.travelplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbRememberMe;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        databaseHelper = new DatabaseHelper(this);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        loadSavedEmail();

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("invalid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("enter password");
            etPassword.requestFocus();
            return;
        }

        // encrypt password before checking login

        String encryptedPassword =
                PasswordHelper.hashPassword(password);

        boolean userExists =
                databaseHelper.checkUser(
                        email,
                        encryptedPassword
                );

        if (!userExists) {

            Toast.makeText(
                    this,
                    "invalid email or password",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (cbRememberMe.isChecked()) {

            SharedPreferences preferences =
                    getSharedPreferences("travel_app", MODE_PRIVATE);

            preferences.edit()
                    .putString("saved_email", email)
                    .apply();
        }

        Toast.makeText(
                this,
                "login successful",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent =
                new Intent(LoginActivity.this, HomeActivity.class);

        startActivity(intent);
        finish();
    }

    private void loadSavedEmail() {

        SharedPreferences preferences =
                getSharedPreferences("travel_app", MODE_PRIVATE);

        String savedEmail =
                preferences.getString("saved_email", "");

        etEmail.setText(savedEmail);
    }
}