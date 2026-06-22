package com.example.travelplanner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SpecialSectionActivity extends AppCompatActivity {

    private RecyclerView recyclerSpecialTrips;
    private TextView txtEmptySpecial;

    private ArrayList<Trip> specialTrips;
    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_section);

        recyclerSpecialTrips =
                findViewById(R.id.recyclerSpecialTrips);

        txtEmptySpecial =
                findViewById(R.id.txtEmptySpecial);

        specialTrips =
                new ArrayList<>();

        tripAdapter =
                new TripAdapter(
                        specialTrips,
                        trip -> openTripDetails(trip)
                );

        recyclerSpecialTrips.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerSpecialTrips.setAdapter(tripAdapter);

        loadSpecialTrips();
    }

    private void loadSpecialTrips() {

        specialTrips.clear();

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        Cursor cursor =
                databaseHelper.getAllTrips();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                double rating =
                        cursor.getDouble(5);

                if (rating >= 4.8) {

                    Trip trip =
                            new Trip(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getInt(3),
                                    cursor.getDouble(4),
                                    rating,
                                    cursor.getString(6),
                                    cursor.getString(7)
                            );

                    specialTrips.add(trip);
                }

            } while (cursor.moveToNext());

            cursor.close();
        }

        tripAdapter.notifyDataSetChanged();

        if (specialTrips.isEmpty()) {

            txtEmptySpecial.setVisibility(View.VISIBLE);
            recyclerSpecialTrips.setVisibility(View.GONE);

        } else {

            txtEmptySpecial.setVisibility(View.GONE);
            recyclerSpecialTrips.setVisibility(View.VISIBLE);
        }
    }

    private void openTripDetails(Trip trip) {

        Intent intent =
                new Intent(
                        SpecialSectionActivity.this,
                        TripDetailsActivity.class
                );

        intent.putExtra("destination", trip.getDestination());
        intent.putExtra("country", trip.getCountry());
        intent.putExtra("duration", trip.getDurationDays());
        intent.putExtra("price", trip.getPrice());
        intent.putExtra("rating", trip.getRating());
        intent.putExtra("description", trip.getDescription());
        intent.putExtra("image", trip.getImage());

        startActivity(intent);
    }
}