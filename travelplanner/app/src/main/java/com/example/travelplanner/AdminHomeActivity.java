package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button btnAddTrip;
    private Button btnManageTrips;
    private Button btnAddAdmin;
    private Button btnViewUsers;
    private Button btnViewReservations;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Initialize buttons
        btnAddTrip = findViewById(R.id.btnAddTrip);
        btnManageTrips = findViewById(R.id.btnManageTrips);
        btnAddAdmin = findViewById(R.id.btnAddAdmin);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnViewReservations = findViewById(R.id.btnViewReservations);
        btnLogout = findViewById(R.id.btnLogout);

        // Open add trip screen
        btnAddTrip.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            AdminHomeActivity.this,
                            AdminAddTripActivity.class
                    );

            startActivity(intent);
        });

        // Open trip management screen
        btnManageTrips.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            AdminHomeActivity.this,
                            AdminManageTripsActivity.class
                    );

            startActivity(intent);
        });

        // Open add admin screen
        btnAddAdmin.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            AdminHomeActivity.this,
                            AdminAddAdminActivity.class
                    );

            startActivity(intent);
        });

        // Show registered users
        btnViewUsers.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            AdminHomeActivity.this,
                            AdminUsersActivity.class
                    );

            startActivity(intent);
        });

        // Show all reservations
        btnViewReservations.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            AdminHomeActivity.this,
                            AdminReservationsActivity.class
                    );

            startActivity(intent);
        });

        // Logout and return to login screen
        btnLogout.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            AdminHomeActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);

            finish();
        });
    }
}