package com.example.travelplanner;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminReservationsActivity extends AppCompatActivity {

    private ListView listReservations;
    private DatabaseHelper databaseHelper;
    private ArrayList<String> reservationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reservations);

        listReservations = findViewById(R.id.listReservations);
        databaseHelper = new DatabaseHelper(this);

        loadReservations();
    }

    private void loadReservations() {

        reservationsList = new ArrayList<>();

        Cursor cursor = databaseHelper.getAllReservations();

        if (cursor.getCount() == 0) {
            reservationsList.add("No reservations found");
        } else {
            while (cursor.moveToNext()) {

                String tripName = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String reservationType = cursor.getString(3);
                String reservationDate = cursor.getString(4);

                reservationsList.add(
                        "Trip: " + tripName +
                                "\nQuantity: " + quantity +
                                "\nType: " + reservationType +
                                "\nDate: " + reservationDate
                );
            }
        }

        cursor.close();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        reservationsList
                );

        listReservations.setAdapter(adapter);
    }
}