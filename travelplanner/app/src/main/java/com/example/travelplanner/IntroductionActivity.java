package com.example.travelplanner;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_introduction);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnConnect = findViewById(R.id.btnConnect);

        // connect to api and load trips

        btnConnect.setOnClickListener(v -> {

            TripConnectionAsyncTask connectionAsyncTask =
                    new TripConnectionAsyncTask(IntroductionActivity.this);

            connectionAsyncTask.execute(
                    "https://mocki.io/v1/f3153911-eb21-4b36-8ca7-18ea3c77cc1a"
            );
        });
    }
}