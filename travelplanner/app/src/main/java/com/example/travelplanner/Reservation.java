package com.example.travelplanner;

public class Reservation {

    private int id;
    private String tripName;
    private int quantity;
    private String reservationType;
    private String reservationDate;
    private String status;

    public Reservation(
            int id,
            String tripName,
            int quantity,
            String reservationType,
            String reservationDate,
            String status
    ) {
        this.id = id;
        this.tripName = tripName;
        this.quantity = quantity;
        this.reservationType = reservationType;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTripName() {
        return tripName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReservationType() {
        return reservationType;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getStatus() {
        return status;
    }
}