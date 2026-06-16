package com.example.travelplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerTrips;

    private ArrayList<Trip> tripList;

    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars =
                    insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        recyclerTrips =
                findViewById(R.id.recyclerTrips);

        // create trips list

        tripList = new ArrayList<>();

        // add test trip

        tripList.add(
                new Trip(
                        1,
                        "Paris",
                        "France",
                        5,
                        1200,
                        4.8,
                        "beautiful city",
                        ""
                )
        );

        // create adapter

        tripAdapter =
                new TripAdapter(tripList);

        recyclerTrips.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerTrips.setAdapter(tripAdapter);
    }
}