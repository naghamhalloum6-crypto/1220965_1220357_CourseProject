package com.example.travelplanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TripJsonParser {

    // convert json array to trip array list

    public static List<Trip> getTripsFromJson(String json) {

        List<Trip> trips;

        try {

            JSONArray jsonArray = new JSONArray(json);

            trips = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Trip trip = new Trip(
                        jsonObject.getInt("id"),
                        jsonObject.getString("destination"),
                        jsonObject.getString("country"),
                        jsonObject.getInt("duration_days"),
                        jsonObject.getDouble("price"),
                        jsonObject.getDouble("rating"),
                        jsonObject.getString("description"),
                        jsonObject.getString("image")
                );

                trips.add(trip);
            }

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }

        return trips;
    }
}