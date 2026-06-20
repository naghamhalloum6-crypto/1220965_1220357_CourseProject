package com.example.travelplanner;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddAdminActivity extends AppCompatActivity {

    private EditText etAdminName;
    private EditText etAdminEmail;
    private EditText etAdminPhone;
    private EditText etAdminPassword;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_admin);

        // Initialize database and views
        databaseHelper = new DatabaseHelper(this);

        etAdminName = findViewById(R.id.etAdminName);
        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPhone = findViewById(R.id.etAdminPhone);
        etAdminPassword = findViewById(R.id.etAdminPassword);

        Button btnCreateAdmin =
                findViewById(R.id.btnCreateAdmin);

        // Create new admin account
        btnCreateAdmin.setOnClickListener(v ->
                createAdmin()
        );
    }

    private void createAdmin() {

        String name = etAdminName.getText().toString().trim();
        String email = etAdminEmail.getText().toString().trim();
        String phone = etAdminPhone.getText().toString().trim();
        String password = etAdminPassword.getText().toString().trim();

        if (name.length() < 3) {
            etAdminName.setError("Name must be at least 3 characters");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etAdminEmail.setError("Invalid email");
            return;
        }

        if (phone.isEmpty()) {
            etAdminPhone.setError("Phone is required");
            return;
        }

        if (password.length() < 6) {
            etAdminPassword.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.matches(".*[A-Za-z].*")) {
            etAdminPassword.setError("Password must contain a letter");
            return;
        }

        if (!password.matches(".*[0-9].*")) {
            etAdminPassword.setError("Password must contain a number");
            return;
        }

        // Check if admin email already exists
        if (databaseHelper.emailExists(email)) {
            etAdminEmail.setError("Email already exists");
            return;
        }

        // Encrypt password before saving
        String encryptedPassword =
                PasswordHelper.hashPassword(password);

        // Save admin data into database
        boolean inserted =
                databaseHelper.insertAdmin(
                        name,
                        email,
                        phone,
                        encryptedPassword
                );

        if (inserted) {
            Toast.makeText(
                    this,
                    "admin added successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "failed to add admin",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}