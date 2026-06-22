package com.example.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    public interface OnFavoriteChangedListener {
        void onFavoriteChanged();
    }

    private ArrayList<Trip> favoriteList;
    private OnFavoriteChangedListener listener;

    public FavoriteAdapter(
            ArrayList<Trip> favoriteList,
            OnFavoriteChangedListener listener
    ) {
        this.favoriteList = favoriteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_favorite_trip,
                                parent,
                                false
                        );

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull FavoriteViewHolder holder,
            int position
    ) {
        Trip trip =
                favoriteList.get(position);

        holder.txtDestination.setText(trip.getDestination());
        holder.txtCountry.setText("Country: " + trip.getCountry());
        holder.txtDuration.setText("Duration: " + trip.getDurationDays() + " days");
        holder.txtPrice.setText("Price: $" + trip.getPrice());
        holder.txtRating.setText("Rating: " + trip.getRating());

        holder.itemView.setOnClickListener(v ->
                openTripDetails(
                        holder.itemView.getContext(),
                        trip
                )
        );

        holder.btnReserve.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            holder.itemView.getContext(),
                            ReservationFormActivity.class
                    );

            intent.putExtra(
                    "trip_name",
                    trip.getDestination()
            );

            holder.itemView.getContext()
                    .startActivity(intent);
        });

        holder.btnRemove.setOnClickListener(v -> {

            DatabaseHelper databaseHelper =
                    new DatabaseHelper(
                            holder.itemView.getContext()
                    );

            boolean removed =
                    databaseHelper.deleteFavorite(
                            trip.getId()
                    );

            if (removed) {

                Toast.makeText(
                        holder.itemView.getContext(),
                        "Removed from favorites",
                        Toast.LENGTH_SHORT
                ).show();

                if (listener != null) {
                    listener.onFavoriteChanged();
                }

            } else {

                Toast.makeText(
                        holder.itemView.getContext(),
                        "Failed to remove favorite",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favoriteList == null) {
            return 0;
        }

        return favoriteList.size();
    }

    private void openTripDetails(
            Context context,
            Trip trip
    ) {
        Intent intent =
                new Intent(
                        context,
                        TripDetailsActivity.class
                );

        intent.putExtra("destination", trip.getDestination());
        intent.putExtra("country", trip.getCountry());
        intent.putExtra("duration", trip.getDurationDays());
        intent.putExtra("price", trip.getPrice());
        intent.putExtra("rating", trip.getRating());
        intent.putExtra("description", trip.getDescription());
        intent.putExtra("image", trip.getImage());

        context.startActivity(intent);
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        TextView txtDestination;
        TextView txtCountry;
        TextView txtDuration;
        TextView txtPrice;
        TextView txtRating;
        Button btnReserve;
        Button btnRemove;

        public FavoriteViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtDestination =
                    itemView.findViewById(
                            R.id.txtFavoriteDestination
                    );

            txtCountry =
                    itemView.findViewById(
                            R.id.txtFavoriteCountry
                    );

            txtDuration =
                    itemView.findViewById(
                            R.id.txtFavoriteDuration
                    );

            txtPrice =
                    itemView.findViewById(
                            R.id.txtFavoritePrice
                    );

            txtRating =
                    itemView.findViewById(
                            R.id.txtFavoriteRating
                    );

            btnReserve =
                    itemView.findViewById(
                            R.id.btnReserveFavorite
                    );

            btnRemove =
                    itemView.findViewById(
                            R.id.btnRemoveFavorite
                    );
        }
    }
}