package com.example.travelplanner;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservationsActivity extends AppCompatActivity {

    private TextView txtNoReservations;
    private RecyclerView recyclerReservations;

    private ArrayList<Reservation> reservations;
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        txtNoReservations =
                findViewById(R.id.txtNoReservations);

        recyclerReservations =
                findViewById(R.id.recyclerReservations);

        reservations =
                new ArrayList<>();

        reservationAdapter =
                new ReservationAdapter(
                        reservations
                );

        recyclerReservations.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerReservations.setAdapter(
                reservationAdapter
        );

        loadReservations();
    }

    private void loadReservations() {

        reservations.clear();

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        Cursor cursor =
                databaseHelper.getAllReservations();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                Reservation reservation =
                        new Reservation(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                "Confirmed"
                        );

                reservations.add(reservation);

            } while (cursor.moveToNext());

            cursor.close();
        }

        reservationAdapter.notifyDataSetChanged();

        if (reservations.isEmpty()) {

            txtNoReservations.setVisibility(View.VISIBLE);
            recyclerReservations.setVisibility(View.GONE);

        } else {

            txtNoReservations.setVisibility(View.GONE);
            recyclerReservations.setVisibility(View.VISIBLE);
        }
    }
}