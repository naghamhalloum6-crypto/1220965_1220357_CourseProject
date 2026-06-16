package com.example.travelplanner;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerTrips;

    private EditText etSearch;

    private ArrayList<Trip> tripList;

    private ArrayList<Trip> allTrips;

    private TripAdapter tripAdapter;

    private static final String API_URL =
            "https://mocki.io/v1/f3153911-eb21-4b36-8ca7-18ea3c77cc1a";

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

        etSearch =
                findViewById(R.id.etSearch);

        tripList = new ArrayList<>();

        allTrips = new ArrayList<>();

        tripAdapter =
                new TripAdapter(tripList);

        recyclerTrips.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerTrips.setAdapter(tripAdapter);

        // load trips from api

        loadTripsFromApi();

        // search trips by destination

        etSearch.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {

                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        filterTrips(
                                s.toString()
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {

                    }
                }
        );
    }

    // get trips from api

    private void loadTripsFromApi() {

        new Thread(() -> {

            String json =
                    HttpManager.getData(API_URL);

            if (json == null) {

                runOnUiThread(() ->
                        Toast.makeText(
                                HomeActivity.this,
                                "failed to load trips",
                                Toast.LENGTH_SHORT
                        ).show()
                );

                return;
            }

            List<Trip> trips =
                    TripJsonParser.getTripsFromJson(json);

            if (trips == null) {

                runOnUiThread(() ->
                        Toast.makeText(
                                HomeActivity.this,
                                "invalid trip data",
                                Toast.LENGTH_SHORT
                        ).show()
                );

                return;
            }

            runOnUiThread(() -> {

                tripList.clear();
                allTrips.clear();

                tripList.addAll(trips);
                allTrips.addAll(trips);

                tripAdapter.notifyDataSetChanged();

                Toast.makeText(
                        HomeActivity.this,
                        "trips loaded successfully",
                        Toast.LENGTH_SHORT
                ).show();
            });

        }).start();
    }

    // filter trips by destination

    private void filterTrips(String keyword) {

        tripList.clear();

        // show all trips if search is empty

        if (keyword.isEmpty()) {

            tripList.addAll(allTrips);

        } else {

            for (Trip trip : allTrips) {

                if (trip.getDestination()
                        .toLowerCase()
                        .contains(
                                keyword.toLowerCase()
                        )) {

                    tripList.add(trip);
                }
            }
        }

        tripAdapter.notifyDataSetChanged();
    }
}