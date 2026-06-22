package com.example.travelplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgProfile;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etNewPassword;

    private Button btnChooseImage;
    private Button btnUpdateProfile;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setImageResource(R.mipmap.ic_launcher);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etNewPassword = findViewById(R.id.etNewPassword);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        // Open gallery to choose profile picture
        btnChooseImage.setOnClickListener(v ->
                openGallery()
        );

        // Update profile information
        btnUpdateProfile.setOnClickListener(v ->
                updateProfile()
        );
    }

    private void openGallery() {

        Toast.makeText(
                this,
                "Select a profile picture",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent =
                new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");

        startActivityForResult(
                intent,
                PICK_IMAGE_REQUEST
        );
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            selectedImageUri =
                    data.getData();

            imgProfile.setImageURI(
                    selectedImageUri
            );

            Toast.makeText(
                    this,
                    "Profile picture selected",
                    Toast.LENGTH_SHORT
            ).show();

        } else if (requestCode == PICK_IMAGE_REQUEST) {

            Toast.makeText(
                    this,
                    "No profile picture selected",
                    Toast.LENGTH_SHORT
            ).show();
        }
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

        // Validate password if user enters a new one
        if (!password.isEmpty()) {

            if (password.length() < 6) {

                etNewPassword.setError(
                        "Password must be at least 6 characters"
                );

                return;
            }

            if (!password.matches(".*[A-Za-z].*")) {

                etNewPassword.setError(
                        "Password must contain a letter"
                );

                return;
            }

            if (!password.matches(".*[0-9].*")) {

                etNewPassword.setError(
                        "Password must contain a number"
                );

                return;
            }
        }

        Toast.makeText(
                this,
                "Profile updated successfully",
                Toast.LENGTH_SHORT
        ).show();
    }
}