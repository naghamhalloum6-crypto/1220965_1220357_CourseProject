package com.example.travelplanner;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_trip);

        // Temporary test message
        Toast.makeText(
                this,
                "Add Trip page opened",
                Toast.LENGTH_SHORT
        ).show();
    }
}