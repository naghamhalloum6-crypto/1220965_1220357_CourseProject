package com.example.travelplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    // list of trips

    private ArrayList<Trip> tripList;

    public TripAdapter(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        // create trip item view

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

        // get current trip

        Trip trip = tripList.get(position);

        // display trip data

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
    }

    @Override
    public int getItemCount() {

        // prevent crash if list is null

        if (tripList == null) {
            return 0;
        }

        return tripList.size();
    }

    // view holder class

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