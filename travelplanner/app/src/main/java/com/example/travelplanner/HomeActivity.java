package com.example.travelplanner;

import com.google.android.material.navigation.NavigationView;
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
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerTrips;

    private EditText etSearch;

    private Spinner spCountryFilter;
    private android.widget.TextView txtEmptyResult;

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
        DrawerLayout drawerLayout =
                findViewById(R.id.main);

        Button btnOpenDrawer =
                findViewById(R.id.btnOpenDrawer);

// Open navigation drawer
        btnOpenDrawer.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );

        // Setup navigation drawer
        NavigationView navigationView =
                findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {

                Toast.makeText(
                        this,
                        "Home",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (itemId == R.id.nav_trips) {

                Toast.makeText(
                        this,
                        "Trips",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (itemId == R.id.nav_reservations) {

                Toast.makeText(
                        this,
                        "My Reservations",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (itemId == R.id.nav_favorites) {

                Intent intent =
                        new Intent(
                                HomeActivity.this,
                                FavoritesActivity.class
                        );

                startActivity(intent);
            }else if (itemId == R.id.nav_special) {

                Toast.makeText(
                        this,
                        "Special Section",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (itemId == R.id.nav_profile) {

                Toast.makeText(
                        this,
                        "Profile Management",
                        Toast.LENGTH_SHORT
                ).show();

            } else if (itemId == R.id.nav_contact) {

                Intent intent =
                        new Intent(
                                HomeActivity.this,
                                ContactUsActivity.class
                        );

                startActivity(intent);
            } else if (itemId == R.id.nav_logout) {

                Toast.makeText(
                        this,
                        "Logged out",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(
                                HomeActivity.this,
                                LoginActivity.class
                        );

                startActivity(intent);

                finish();
            }

            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

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
                }
        );

        recyclerTrips =
                findViewById(R.id.recyclerTrips);

        etSearch =
                findViewById(R.id.etSearch);

        spCountryFilter =
                findViewById(R.id.spCountryFilter);

        txtEmptyResult =
                findViewById(R.id.txtEmptyResult);

        tripList = new ArrayList<>();

        allTrips = new ArrayList<>();

        tripAdapter =
                new TripAdapter(
                        tripList,
                        trip -> {

                            Intent intent =
                                    new Intent(
                                            HomeActivity.this,
                                            TripDetailsActivity.class
                                    );

                            intent.putExtra(
                                    "destination",
                                    trip.getDestination()
                            );

                            intent.putExtra(
                                    "country",
                                    trip.getCountry()
                            );

                            intent.putExtra(
                                    "duration",
                                    trip.getDurationDays()
                            );

                            intent.putExtra(
                                    "price",
                                    trip.getPrice()
                            );

                            intent.putExtra(
                                    "rating",
                                    trip.getRating()
                            );

                            intent.putExtra(
                                    "description",
                                    trip.getDescription()
                            );

                            intent.putExtra(
                                    "image",
                                    trip.getImage()
                            );

                            startActivity(intent);
                        }
                );

        recyclerTrips.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerTrips.setAdapter(tripAdapter);

        setupCountryFilter();

        loadTripsFromApi();

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
                        applyFilters();
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {
                    }
                }
        );
    }

    // load trips from api

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

                DatabaseHelper databaseHelper =
                        new DatabaseHelper(
                                HomeActivity.this
                        );

                for (Trip trip : trips) {

                    if (!databaseHelper.tripExists(
                            trip.getId()
                    )) {

                        databaseHelper.insertTrip(
                                trip.getId(),
                                trip.getDestination(),
                                trip.getCountry(),
                                trip.getDurationDays(),
                                trip.getPrice(),
                                trip.getRating(),
                                trip.getDescription(),
                                trip.getImage()
                        );
                    }
                }

                applyFilters();

                Toast.makeText(
                        HomeActivity.this,
                        "trips loaded successfully",
                        Toast.LENGTH_SHORT
                ).show();
            });

        }).start();
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

                        applyFilters();
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {

                    }
                }
        );
    }

    // combine search and filter

    private void applyFilters() {

        String keyword =
                etSearch.getText()
                        .toString()
                        .trim()
                        .toLowerCase();

        String selectedCountry =
                spCountryFilter.getSelectedItem()
                        .toString();

        tripList.clear();

        for (Trip trip : allTrips) {

            boolean matchesCountry =
                    selectedCountry.equals("All Countries")
                            ||
                            trip.getCountry()
                                    .equalsIgnoreCase(
                                            selectedCountry
                                    );

            boolean matchesSearch =
                    trip.getDestination()
                            .toLowerCase()
                            .contains(keyword);

            if (matchesCountry && matchesSearch) {

                tripList.add(trip);
            }
        }

        tripAdapter.notifyDataSetChanged();

        if (tripList.isEmpty()) {

            txtEmptyResult.setVisibility(
                    View.VISIBLE
            );

        } else {

            txtEmptyResult.setVisibility(
                    View.GONE
            );
        }
    }
}