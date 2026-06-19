package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button btnAddTrip;
    private Button btnViewUsers;
    private Button btnViewReservations;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnAddTrip = findViewById(R.id.btnAddTrip);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnViewReservations = findViewById(R.id.btnViewReservations);
        btnLogout = findViewById(R.id.btnLogout);

        // Open admin pages
        btnAddTrip.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminAddTripActivity.class);
            startActivity(intent);
        });

        btnViewUsers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminUsersActivity.class);
            startActivity(intent);
        });

        btnViewReservations.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminReservationsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}