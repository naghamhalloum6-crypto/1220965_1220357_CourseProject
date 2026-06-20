package com.example.travelplanner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TripDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        if (savedInstanceState == null) {

            // Display trip details inside fragment
            TripDetailsFragment fragment =
                    new TripDetailsFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragmentContainer,
                            fragment
                    )
                    .commit();
        }
    }
}