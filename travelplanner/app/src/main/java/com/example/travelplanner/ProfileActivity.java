package com.example.travelplanner;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private DatabaseHelper databaseHelper;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences preferences =
                getSharedPreferences("travel_app", MODE_PRIVATE);

        currentUserEmail =
                preferences.getString("current_user_email", "");

        String emailFromIntent =
                getIntent().getStringExtra("user_email");

        if (emailFromIntent != null && !emailFromIntent.isEmpty()) {
            currentUserEmail = emailFromIntent;
        }

        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setImageResource(R.mipmap.ic_launcher);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etNewPassword = findViewById(R.id.etNewPassword);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        btnChooseImage.setOnClickListener(v ->
                openGallery()
        );

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
                new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Profile Picture"
                ),
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

            selectedImageUri = data.getData();

            imgProfile.setImageURI(selectedImageUri);

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

        if (currentUserEmail == null || currentUserEmail.isEmpty()) {

            Toast.makeText(
                    this,
                    "User email not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (firstName.length() < 3) {

            etFirstName.setError(
                    "First name must be at least 3 characters"
            );

            return;
        }

        if (lastName.length() < 3) {

            etLastName.setError(
                    "Last name must be at least 3 characters"
            );

            return;
        }

        if (phone.isEmpty()) {

            etPhone.setError(
                    "Phone number is required"
            );

            return;
        }

        String encryptedPassword = "";

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

            encryptedPassword =
                    PasswordHelper.hashPassword(password);
        }

        boolean updated =
                databaseHelper.updateUserProfile(
                        currentUserEmail,
                        firstName,
                        lastName,
                        phone,
                        encryptedPassword
                );

        if (updated) {

            Toast.makeText(
                    this,
                    "Profile updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update profile",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}