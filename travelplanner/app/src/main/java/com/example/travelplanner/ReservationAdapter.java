package com.example.travelplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private ArrayList<Reservation> reservationList;

    public ReservationAdapter(
            ArrayList<Reservation> reservationList
    ) {
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_reservation,
                                parent,
                                false
                        );

        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ReservationViewHolder holder,
            int position
    ) {
        Reservation reservation =
                reservationList.get(position);

        holder.txtTrip.setText(
                "Trip: " + reservation.getTripName()
        );

        holder.txtStatus.setText(
                "Status: " + reservation.getStatus()
        );

        holder.txtQuantity.setText(
                "Quantity: " + reservation.getQuantity()
        );

        holder.txtType.setText(
                "Type: " + reservation.getReservationType()
        );

        holder.txtDate.setText(
                "Date: " + reservation.getReservationDate()
        );
    }

    @Override
    public int getItemCount() {
        if (reservationList == null) {
            return 0;
        }

        return reservationList.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {

        TextView txtTrip;
        TextView txtStatus;
        TextView txtQuantity;
        TextView txtType;
        TextView txtDate;

        public ReservationViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtTrip =
                    itemView.findViewById(
                            R.id.txtReservationTrip
                    );

            txtStatus =
                    itemView.findViewById(
                            R.id.txtReservationStatus
                    );

            txtQuantity =
                    itemView.findViewById(
                            R.id.txtReservationQuantity
                    );

            txtType =
                    itemView.findViewById(
                            R.id.txtReservationType
                    );

            txtDate =
                    itemView.findViewById(
                            R.id.txtReservationDate
                    );
        }
    }
}