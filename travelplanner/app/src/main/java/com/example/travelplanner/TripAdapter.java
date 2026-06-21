package com.example.travelplanner;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    public interface OnTripClickListener {
        void onTripClick(Trip trip);
    }

    private ArrayList<Trip> tripList;
    private OnTripClickListener listener;

    public TripAdapter(
            ArrayList<Trip> tripList,
            OnTripClickListener listener
    ) {
        this.tripList = tripList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_trip,
                        parent,
                        false
                );

        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TripViewHolder holder,
            int position
    ) {
        Trip trip = tripList.get(position);

        holder.txtDestination.setText(trip.getDestination());
        holder.txtCountry.setText("Country: " + trip.getCountry());
        holder.txtDuration.setText("Duration: " + trip.getDurationDays() + " days");
        holder.txtPrice.setText("Price: $" + trip.getPrice());
        holder.txtRating.setText("Rating: " + trip.getRating());

        // Open trip details
        holder.itemView.setOnClickListener(v ->
                listener.onTripClick(trip)
        );

        // Add trip to favorites
        holder.btnFavorite.setOnClickListener(v -> {

            DatabaseHelper databaseHelper =
                    new DatabaseHelper(holder.itemView.getContext());

            boolean added =
                    databaseHelper.insertFavorite(
                            trip.getId(),
                            trip.getDestination(),
                            trip.getCountry(),
                            trip.getDurationDays(),
                            trip.getPrice(),
                            trip.getRating(),
                            trip.getDescription(),
                            trip.getImage()
                    );

            if (added) {
                Toast.makeText(
                        holder.itemView.getContext(),
                        "Added to favorites",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        holder.itemView.getContext(),
                        "Already in favorites",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tripList == null) {
            return 0;
        }

        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        TextView txtDestination;
        TextView txtCountry;
        TextView txtDuration;
        TextView txtPrice;
        TextView txtRating;
        Button btnFavorite;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtRating = itemView.findViewById(R.id.txtRating);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}