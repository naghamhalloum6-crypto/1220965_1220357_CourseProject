package com.example.travelplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminEditTripActivity extends AppCompatActivity {

    private EditText etDestination, etCountry, etDuration;
    private EditText etPrice, etRating, etDescription, etImage;

    private DatabaseHelper databaseHelper;
    private int tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_trip);

        databaseHelper = new DatabaseHelper(this);

        etDestination = findViewById(R.id.etDestination);
        etCountry = findViewById(R.id.etCountry);
        etDuration = findViewById(R.id.etDuration);
        etPrice = findViewById(R.id.etPrice);
        etRating = findViewById(R.id.etRating);
        etDescription = findViewById(R.id.etDescription);
        etImage = findViewById(R.id.etImage);

        Button btnUpdateTrip = findViewById(R.id.btnUpdateTrip);

        tripId = getIntent().getIntExtra("trip_id", -1);

        etDestination.setText(getIntent().getStringExtra("destination"));
        etCountry.setText(getIntent().getStringExtra("country"));
        etDuration.setText(String.valueOf(getIntent().getIntExtra("duration", 0)));
        etPrice.setText(String.valueOf(getIntent().getDoubleExtra("price", 0)));
        etRating.setText(String.valueOf(getIntent().getDoubleExtra("rating", 0)));
        etDescription.setText(getIntent().getStringExtra("description"));
        etImage.setText(getIntent().getStringExtra("image"));

        btnUpdateTrip.setOnClickListener(v -> updateTrip());
    }

    private void updateTrip() {

        String destination = etDestination.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String durationText = etDuration.getText().toString().trim();
        String priceText = etPrice.getText().toString().trim();
        String ratingText = etRating.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String image = etImage.getText().toString().trim();

        if (destination.isEmpty() || country.isEmpty()
                || durationText.isEmpty() || priceText.isEmpty()
                || ratingText.isEmpty() || description.isEmpty()
                || image.isEmpty()) {

            Toast.makeText(
                    this,
                    "please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        int duration = Integer.parseInt(durationText);
        double price = Double.parseDouble(priceText);
        double rating = Double.parseDouble(ratingText);

        // Update trip in database
        boolean updated = databaseHelper.updateTrip(
                tripId,
                destination,
                country,
                duration,
                price,
                rating,
                description,
                image
        );

        if (updated) {
            Toast.makeText(
                    this,
                    "trip updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "failed to update trip",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}