package com.example.travelplanner;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class TripDetailsActivity extends AppCompatActivity {

    private ImageView imgTrip;

    private TextView txtDestination;
    private TextView txtCountry;
    private TextView txtDuration;
    private TextView txtPrice;
    private TextView txtRating;
    private TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_details);

        imgTrip =
                findViewById(R.id.imgTrip);

        txtDestination =
                findViewById(R.id.txtDestination);

        txtCountry =
                findViewById(R.id.txtCountry);

        txtDuration =
                findViewById(R.id.txtDuration);

        txtPrice =
                findViewById(R.id.txtPrice);

        txtRating =
                findViewById(R.id.txtRating);

        txtDescription =
                findViewById(R.id.txtDescription);

        String destination =
                getIntent().getStringExtra(
                        "destination"
                );

        String country =
                getIntent().getStringExtra(
                        "country"
                );

        int duration =
                getIntent().getIntExtra(
                        "duration",
                        0
                );

        double price =
                getIntent().getDoubleExtra(
                        "price",
                        0
                );

        double rating =
                getIntent().getDoubleExtra(
                        "rating",
                        0
                );

        String description =
                getIntent().getStringExtra(
                        "description"
                );

        String imageUrl =
                getIntent().getStringExtra(
                        "image"
                );

        txtDestination.setText(
                destination
        );

        txtCountry.setText(
                "Country: " + country
        );

        txtDuration.setText(
                "Duration: " +
                        duration +
                        " days"
        );

        txtPrice.setText(
                "Price: $" + price
        );

        txtRating.setText(
                "Rating: " + rating
        );

        txtDescription.setText(
                description
        );

        Glide.with(this)
                .load(imageUrl)
                .into(imgTrip);
    }
}