package com.example.travelplanner;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerFavorites;
    private TextView txtEmptyFavorites;

    private ArrayList<Trip> favoriteTrips;
    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerFavorites =
                findViewById(R.id.recyclerFavorites);

        txtEmptyFavorites =
                findViewById(R.id.txtEmptyFavorites);

        favoriteTrips =
                new ArrayList<>();

        tripAdapter =
                new TripAdapter(
                        favoriteTrips,
                        trip -> {
                            // Open selected favorite trip details
                            android.content.Intent intent =
                                    new android.content.Intent(
                                            FavoritesActivity.this,
                                            TripDetailsActivity.class
                                    );

                            intent.putExtra(
                                    "destination",
                                    trip.getDestination()
                            );

                            intent.putExtra(
                                    "country",
                                    trip.getCountry()
                            );

                            intent.putExtra(
                                    "duration",
                                    trip.getDurationDays()
                            );

                            intent.putExtra(
                                    "price",
                                    trip.getPrice()
                            );

                            intent.putExtra(
                                    "rating",
                                    trip.getRating()
                            );

                            intent.putExtra(
                                    "description",
                                    trip.getDescription()
                            );

                            intent.putExtra(
                                    "image",
                                    trip.getImage()
                            );

                            startActivity(intent);
                        }
                );

        recyclerFavorites.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerFavorites.setAdapter(tripAdapter);

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadFavorites();
    }

    private void loadFavorites() {

        favoriteTrips.clear();

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        Cursor cursor =
                databaseHelper.getAllFavorites();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                Trip trip =
                        new Trip(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getInt(3),
                                cursor.getDouble(4),
                                cursor.getDouble(5),
                                cursor.getString(6),
                                cursor.getString(7)
                        );

                favoriteTrips.add(trip);

            } while (cursor.moveToNext());

            cursor.close();
        }

        tripAdapter.notifyDataSetChanged();

        if (favoriteTrips.isEmpty()) {

            txtEmptyFavorites.setVisibility(View.VISIBLE);
            recyclerFavorites.setVisibility(View.GONE);

        } else {

            txtEmptyFavorites.setVisibility(View.GONE);
            recyclerFavorites.setVisibility(View.VISIBLE);
        }
    }
}