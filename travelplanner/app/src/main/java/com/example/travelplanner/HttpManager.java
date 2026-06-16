package com.example.travelplanner;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    // get json data from api

    public static String getData(String urlAddress) {

        BufferedReader bufferedReader = null;

        try {

            URL url = new URL(urlAddress);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();

            // check response code

            if (responseCode != HttpURLConnection.HTTP_OK) {

                return null;
            }

            bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder stringBuilder = new StringBuilder();

            String line = bufferedReader.readLine();

            while (line != null) {

                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            return stringBuilder.toString();

        } catch (Exception e) {

            Log.e("HttpManager", e.toString());
            return null;
        }
    }
}