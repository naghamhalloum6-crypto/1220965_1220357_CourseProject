package com.example.travelplanner;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminManageTripsActivity extends AppCompatActivity {

    private LinearLayout layoutTrips;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_trips);

        layoutTrips = findViewById(R.id.layoutTrips);
        databaseHelper = new DatabaseHelper(this);

        loadTrips();
    }

    @Override
    protected void onResume() {
        super.onResume();

        layoutTrips.removeAllViews();
        loadTrips();
    }

    private void loadTrips() {

        Cursor cursor = databaseHelper.getAllTrips();

        while (cursor.moveToNext()) {

            int tripId = cursor.getInt(0);
            String destination = cursor.getString(1);
            String country = cursor.getString(2);
            int duration = cursor.getInt(3);
            double price = cursor.getDouble(4);
            double rating = cursor.getDouble(5);
            String description = cursor.getString(6);
            String image = cursor.getString(7);

            TextView txtTrip = new TextView(this);

            txtTrip.setText(destination + " (ID: " + tripId + ")");
            txtTrip.setTextSize(18);

            Button btnEdit = new Button(this);
            btnEdit.setText("Edit Trip");

            btnEdit.setOnClickListener(v -> {

                Intent intent =
                        new Intent(AdminManageTripsActivity.this,
                                AdminEditTripActivity.class);

                intent.putExtra("trip_id", tripId);
                intent.putExtra("destination", destination);
                intent.putExtra("country", country);
                intent.putExtra("duration", duration);
                intent.putExtra("price", price);
                intent.putExtra("rating", rating);
                intent.putExtra("description", description);
                intent.putExtra("image", image);

                startActivity(intent);
            });

            Button btnDelete = new Button(this);
            btnDelete.setText("Delete Trip");

            btnDelete.setOnClickListener(v -> {

                new AlertDialog.Builder(this)
                        .setTitle("Delete Trip")
                        .setMessage("Delete this trip?")
                        .setPositiveButton("Delete", (dialog, which) -> {

                            boolean deleted =
                                    databaseHelper.deleteTrip(tripId);

                            if (deleted) {
                                Toast.makeText(
                                        this,
                                        "trip deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                recreate();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

            layoutTrips.addView(txtTrip);
            layoutTrips.addView(btnEdit);
            layoutTrips.addView(btnDelete);
        }

        cursor.close();
    }
}