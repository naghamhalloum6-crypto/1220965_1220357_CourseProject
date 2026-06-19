package com.example.travelplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etNewPassword;

    private Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etFirstName =
                findViewById(R.id.etFirstName);

        etLastName =
                findViewById(R.id.etLastName);

        etPhone =
                findViewById(R.id.etPhone);

        etNewPassword =
                findViewById(R.id.etNewPassword);

        btnUpdateProfile =
                findViewById(R.id.btnUpdateProfile);

        // Update profile information
        btnUpdateProfile.setOnClickListener(v ->
                updateProfile()
        );
    }

    private void updateProfile() {

        String firstName =
                etFirstName.getText()
                        .toString()
                        .trim();

        String lastName =
                etLastName.getText()
                        .toString()
                        .trim();

        String phone =
                etPhone.getText()
                        .toString()
                        .trim();

        String password =
                etNewPassword.getText()
                        .toString()
                        .trim();

        // Validate first name
        if (firstName.length() < 3) {

            etFirstName.setError(
                    "First name must be at least 3 characters"
            );

            return;
        }

        // Validate last name
        if (lastName.length() < 3) {

            etLastName.setError(
                    "Last name must be at least 3 characters"
            );

            return;
        }

        // Validate phone number
        if (phone.isEmpty()) {

            etPhone.setError(
                    "Phone number is required"
            );

            return;
        }

        // Validate password length
        if (!password.isEmpty()
                && password.length() < 6) {

            etNewPassword.setError(
                    "Password must be at least 6 characters"
            );

            return;
        }

        // Show success message
        Toast.makeText(
                this,
                "Profile updated successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}