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
    private SharedPreferences preferences;

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
        preferences = getSharedPreferences("travel_app", MODE_PRIVATE);

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

        if (email.equals("admin@admin.com") && password.equals("Admin123!")) {

            saveLoginInfo(email);

            Intent intent =
                    new Intent(LoginActivity.this, AdminHomeActivity.class);

            startActivity(intent);
            finish();
            return;
        }

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

        saveLoginInfo(email);

        Toast.makeText(
                this,
                "login successful",
                Toast.LENGTH_SHORT
        ).show();

        String category =
                databaseHelper.getUserCategory(email);

        Intent intent;

        if (category.equals("Admin")) {
            intent =
                    new Intent(
                            LoginActivity.this,
                            AdminHomeActivity.class
                    );
        } else {
            intent =
                    new Intent(
                            LoginActivity.this,
                            HomeActivity.class
                    );
        }

        startActivity(intent);
        finish();
    }

    private void saveLoginInfo(String email) {

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putString("current_user_email", email);

        if (cbRememberMe.isChecked()) {
            editor.putString("saved_email", email);
        } else {
            editor.remove("saved_email");
        }

        editor.apply();
    }

    private void loadSavedEmail() {

        String savedEmail =
                preferences.getString("saved_email", "");

        etEmail.setText(savedEmail);

        if (!savedEmail.isEmpty()) {
            cbRememberMe.setChecked(true);
        }
    }
}