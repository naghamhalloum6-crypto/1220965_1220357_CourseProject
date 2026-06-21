package com.example.travelplanner;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationsActivity extends AppCompatActivity {

    private TextView txtNoReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        txtNoReservations =
                findViewById(R.id.txtNoReservations);

        loadReservations();
    }

    private void loadReservations() {

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        Cursor cursor =
                databaseHelper.getAllReservations();

        if (cursor != null && cursor.moveToFirst()) {

            StringBuilder reservationsText =
                    new StringBuilder();

            do {
                reservationsText.append("Trip: ")
                        .append(cursor.getString(1))
                        .append("\nQuantity: ")
                        .append(cursor.getInt(2))
                        .append("\nType: ")
                        .append(cursor.getString(3))
                        .append("\nDate: ")
                        .append(cursor.getString(4))
                        .append("\n\n");

            } while (cursor.moveToNext());

            txtNoReservations.setText(
                    reservationsText.toString()
            );

            cursor.close();

        } else {

            txtNoReservations.setText(
                    "No reservations found"
            );
        }
    }
}