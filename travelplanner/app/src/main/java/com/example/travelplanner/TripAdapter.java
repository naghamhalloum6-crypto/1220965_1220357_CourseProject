package com.example.travelplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    // click listener

    public interface OnTripClickListener {

        void onTripClick(Trip trip);
    }

    // list of trips

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

        holder.txtDestination.setText(
                trip.getDestination()
        );

        holder.txtCountry.setText(
                "Country: " + trip.getCountry()
        );

        holder.txtDuration.setText(
                "Duration: " +
                        trip.getDurationDays() +
                        " days"
        );

        holder.txtPrice.setText(
                "Price: $" +
                        trip.getPrice()
        );

        holder.txtRating.setText(
                "Rating: " +
                        trip.getRating()
        );

        holder.itemView.setOnClickListener(v -> {

            listener.onTripClick(trip);

        });
    }

    @Override
    public int getItemCount() {

        if (tripList == null) {
            return 0;
        }

        return tripList.size();
    }

    public static class TripViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtDestination;
        TextView txtCountry;
        TextView txtDuration;
        TextView txtPrice;
        TextView txtRating;

        public TripViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtDestination =
                    itemView.findViewById(
                            R.id.txtDestination
                    );

            txtCountry =
                    itemView.findViewById(
                            R.id.txtCountry
                    );

            txtDuration =
                    itemView.findViewById(
                            R.id.txtDuration
                    );

            txtPrice =
                    itemView.findViewById(
                            R.id.txtPrice
                    );

            txtRating =
                    itemView.findViewById(
                            R.id.txtRating
                    );
        }
    }
}