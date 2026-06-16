package com.example.travelplanner;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    private Spinner spCountryFilter;

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

        spCountryFilter =
                findViewById(R.id.spCountryFilter);

        tripList = new ArrayList<>();

        allTrips = new ArrayList<>();

        tripAdapter =
                new TripAdapter(tripList);

        recyclerTrips.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerTrips.setAdapter(tripAdapter);

        setupCountryFilter();

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

// setup country filter

    private void setupCountryFilter() {

        String[] countries = {
                "All Countries",
                "Turkey",
                "France",
                "Italy",
                "UAE",
                "United Kingdom",
                "Spain",
                "Japan",
                "Greece",
                "Egypt",
                "USA"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        countries
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCountryFilter.setAdapter(adapter);

        spCountryFilter.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        String selectedCountry =
                                parent.getItemAtPosition(position)
                                        .toString();

                        filterByCountry(
                                selectedCountry
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {

                    }
                }
        );
    }

// filter trips by country

    private void filterByCountry(String country) {

        tripList.clear();

        if (country.equals("All Countries")) {

            tripList.addAll(allTrips);

        } else {

            for (Trip trip : allTrips) {

                if (trip.getCountry()
                        .equalsIgnoreCase(country)) {

                    tripList.add(trip);
                }
            }
        }

        tripAdapter.notifyDataSetChanged();
    }

}
