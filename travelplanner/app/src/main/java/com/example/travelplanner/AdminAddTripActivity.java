package com.example.travelplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddTripActivity extends AppCompatActivity {

    private EditText etTripId, etDestination, etCountry;
    private EditText etDuration, etPrice, etRating;
    private EditText etDescription, etImage;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_trip);

        databaseHelper = new DatabaseHelper(this);

        etTripId = findViewById(R.id.etTripId);
        etDestination = findViewById(R.id.etDestination);
        etCountry = findViewById(R.id.etCountry);
        etDuration = findViewById(R.id.etDuration);
        etPrice = findViewById(R.id.etPrice);
        etRating = findViewById(R.id.etRating);
        etDescription = findViewById(R.id.etDescription);
        etImage = findViewById(R.id.etImage);

        Button btnSaveTrip = findViewById(R.id.btnSaveTrip);
        btnSaveTrip.setOnClickListener(v -> saveTrip());
    }

    private void saveTrip() {

        String idText = etTripId.getText().toString().trim();
        String destination = etDestination.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String durationText = etDuration.getText().toString().trim();
        String priceText = etPrice.getText().toString().trim();
        String ratingText = etRating.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String image = etImage.getText().toString().trim();

        if (idText.isEmpty() || destination.isEmpty() || country.isEmpty()
                || durationText.isEmpty() || priceText.isEmpty()
                || ratingText.isEmpty() || description.isEmpty()
                || image.isEmpty()) {

            Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idText);
        int duration = Integer.parseInt(durationText);
        double price = Double.parseDouble(priceText);
        double rating = Double.parseDouble(ratingText);

        if (databaseHelper.tripExists(id)) {
            Toast.makeText(this, "trip already exists", Toast.LENGTH_LONG).show();
            return;
        }

        // Save new trip in database
        Toast.makeText(this, "Saving trip...", Toast.LENGTH_SHORT).show();

        boolean inserted = databaseHelper.insertTrip(
                id,
                destination,
                country,
                duration,
                price,
                rating,
                description,
                image
        );

        if (inserted) {
            Toast.makeText(this, "trip added successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "failed to add trip", Toast.LENGTH_LONG).show();
        }
    }
}