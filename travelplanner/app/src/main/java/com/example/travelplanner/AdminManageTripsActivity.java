package com.example.travelplanner;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;

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

    private void loadTrips() {

        Cursor cursor = databaseHelper.getAllTrips();

        while (cursor.moveToNext()) {

            int tripId = cursor.getInt(0);

            String destination = cursor.getString(1);

            TextView txtTrip = new TextView(this);

            txtTrip.setText(
                    destination +
                            " (ID: " + tripId + ")"
            );

            txtTrip.setTextSize(18);

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
            layoutTrips.addView(btnDelete);
        }

        cursor.close();
    }
}