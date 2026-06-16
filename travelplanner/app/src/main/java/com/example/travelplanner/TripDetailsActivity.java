package com.example.travelplanner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TripDetailsActivity extends AppCompatActivity {

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
    }
}