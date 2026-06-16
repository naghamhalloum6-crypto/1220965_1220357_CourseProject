package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;

    private Spinner spGender;
    private Spinner spCategory;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        spGender = findViewById(R.id.spGender);
        spCategory = findViewById(R.id.spCategory);

        databaseHelper = new DatabaseHelper(this);

        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        setupSpinners();

        btnCreateAccount.setOnClickListener(v -> validateInputs());
    }

    private void setupSpinners() {

        String[] genders = {
                "Select Gender",
                "Male",
                "Female"
        };

        String[] categories = {
                "Select Category",
                "Adventure",
                "Family",
                "Business",
                "Luxury",
                "Historical"
        };

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                genders
        );

        genderAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spGender.setAdapter(genderAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCategory.setAdapter(categoryAdapter);
    }

    private void validateInputs() {

        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (firstName.length() < 3) {
            etFirstName.setError("minimum 3 characters");
            etFirstName.requestFocus();
            return;
        }

        if (lastName.length() < 3) {
            etLastName.setError("minimum 3 characters");
            etLastName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("invalid email");
            etEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("required");
            etPhone.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("minimum 6 characters");
            etPassword.requestFocus();
            return;
        }

        if (!password.matches(".*[A-Za-z].*")) {
            etPassword.setError("must contain a letter");
            etPassword.requestFocus();
            return;
        }

        if (!password.matches(".*\\d.*")) {
            etPassword.setError("must contain a number");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        if (spGender.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "select category", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = spGender.getSelectedItem().toString();
        String category = spCategory.getSelectedItem().toString();

        if (databaseHelper.emailExists(email)) {

            etEmail.setError("email already exists");
            etEmail.requestFocus();
            return;
        }

        // encrypt password before saving

        String encryptedPassword =
                PasswordHelper.hashPassword(password);

        boolean inserted = databaseHelper.insertUser(
                firstName,
                lastName,
                email,
                phone,
                gender,
                category,
                encryptedPassword
        );

        if (inserted) {

            Toast.makeText(
                    this,
                    "registration successful",
                    Toast.LENGTH_SHORT
            ).show();

            startActivity(
                    new Intent(
                            SignUpActivity.this,
                            LoginActivity.class
                    )
            );

            finish();

        } else {

            Toast.makeText(
                    this,
                    "registration failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}