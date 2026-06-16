package com.example.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class TripConnectionAsyncTask extends AsyncTask<String, String, String> {

    private Activity activity;

    public TripConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // show loading message

        Toast.makeText(
                activity,
                "connecting...",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    protected String doInBackground(String... params) {

        // get json data from api

        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // check if request failed

        if (result == null || result.isEmpty()) {

            Toast.makeText(
                    activity,
                    "connection failed",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        // convert json to trip list

        List<Trip> trips =
                TripJsonParser.getTripsFromJson(result);

        // check parsing result

        if (trips == null || trips.isEmpty()) {

            Toast.makeText(
                    activity,
                    "no trips found",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        DatabaseHelper databaseHelper =
                new DatabaseHelper(activity);

        int insertedTrips = 0;

        // save trips into sqlite

        for (Trip trip : trips) {

            if (!databaseHelper.tripExists(trip.getId())) {

                boolean inserted =
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

                if (inserted) {
                    insertedTrips++;
                }
            }
        }

        // check if data saved successfully

        if (insertedTrips > 0) {

            Toast.makeText(
                    activity,
                    "trips loaded successfully",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    activity,
                    "trips already exist",
                    Toast.LENGTH_SHORT
            ).show();
        }

        // open login screen

        Intent intent =
                new Intent(activity, LoginActivity.class);

        activity.startActivity(intent);

        activity.finish();
    }
}