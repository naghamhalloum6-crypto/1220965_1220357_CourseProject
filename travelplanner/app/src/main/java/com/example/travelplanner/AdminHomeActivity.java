package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnLogout = findViewById(R.id.btnLogout);

        // Return to login screen
        btnLogout.setOnClickListener(v -> {

            Intent intent =
                    new Intent(AdminHomeActivity.this,
                            LoginActivity.class);

            startActivity(intent);
            finish();
        });
    }
}