package com.example.travelplanner;

import java.security.MessageDigest;

public class PasswordHelper {

    // convert password to hash

    public static String hashPassword(String password) {

        try {

            MessageDigest messageDigest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    messageDigest.digest(password.getBytes());

            StringBuilder stringBuilder =
                    new StringBuilder();

            for (byte b : hashBytes) {

                stringBuilder.append(
                        String.format("%02x", b)
                );
            }

            return stringBuilder.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return password;
        }
    }
}