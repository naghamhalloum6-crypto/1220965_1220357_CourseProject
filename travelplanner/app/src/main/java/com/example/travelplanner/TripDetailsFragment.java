package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class TripDetailsFragment extends Fragment {

    private ImageView imgTrip;
    private TextView txtDestination;
    private TextView txtCountry;
    private TextView txtDuration;
    private TextView txtPrice;
    private TextView txtRating;
    private TextView txtDescription;
    private Button btnReserve;

    private String destination;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view =
                inflater.inflate(
                        R.layout.fragment_trip_details,
                        container,
                        false
                );

        imgTrip = view.findViewById(R.id.imgTrip);
        txtDestination = view.findViewById(R.id.txtDestination);
        txtCountry = view.findViewById(R.id.txtCountry);
        txtDuration = view.findViewById(R.id.txtDuration);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtRating = view.findViewById(R.id.txtRating);
        txtDescription = view.findViewById(R.id.txtDescription);
        btnReserve = view.findViewById(R.id.btnReserve);

        loadTripDetails();

        return view;
    }

    private void loadTripDetails() {

        Bundle bundle = getArguments();

        if (bundle == null) {
            return;
        }

        destination = bundle.getString("destination");
        String country = bundle.getString("country");
        int duration = bundle.getInt("duration", 0);
        double price = bundle.getDouble("price", 0);
        double rating = bundle.getDouble("rating", 0);
        String description = bundle.getString("description");
        String imageUrl = bundle.getString("image");

        txtDestination.setText(destination);
        txtCountry.setText("Country: " + country);
        txtDuration.setText("Duration: " + duration + " days");
        txtPrice.setText("Price: $" + price);
        txtRating.setText("Rating: " + rating);
        txtDescription.setText(description);

        if (imageUrl != null && imageUrl.startsWith("http")) {

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgTrip);

        } else {

            imgTrip.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Open reservation form for this trip
        btnReserve.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            requireActivity(),
                            ReservationFormActivity.class
                    );

            intent.putExtra("trip_name", destination);

            startActivity(intent);
        });
    }
}