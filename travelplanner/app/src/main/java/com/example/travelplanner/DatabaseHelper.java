package com.example.travelplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "travel_planner.db";
    private static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "first_name TEXT," +
                        "last_name TEXT," +
                        "email TEXT UNIQUE," +
                        "phone TEXT," +
                        "gender TEXT," +
                        "category TEXT," +
                        "password TEXT)"
        );

        db.execSQL(
                "CREATE TABLE trips (" +
                        "id INTEGER PRIMARY KEY," +
                        "destination TEXT," +
                        "country TEXT," +
                        "duration_days INTEGER," +
                        "price REAL," +
                        "rating REAL," +
                        "description TEXT," +
                        "image TEXT)"
        );

        db.execSQL(
                "CREATE TABLE reservations (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "trip_name TEXT," +
                        "quantity INTEGER," +
                        "reservation_type TEXT," +
                        "reservation_date TEXT)"
        );

        db.execSQL(
                "CREATE TABLE favorites (" +
                        "id INTEGER PRIMARY KEY," +
                        "destination TEXT," +
                        "country TEXT," +
                        "duration_days INTEGER," +
                        "price REAL," +
                        "rating REAL," +
                        "description TEXT," +
                        "image TEXT)"
        );

        ContentValues admin = new ContentValues();

        admin.put("first_name", "Admin");
        admin.put("last_name", "User");
        admin.put("email", "admin@admin.com");
        admin.put("phone", "0000000000");
        admin.put("gender", "Male");
        admin.put("category", "Admin");
        admin.put("password", PasswordHelper.hashPassword("Admin123!"));

        db.insert("users", null, admin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS trips");
        db.execSQL("DROP TABLE IF EXISTS reservations");
        db.execSQL("DROP TABLE IF EXISTS favorites");

        onCreate(db);
    }

    public boolean insertUser(
            String firstName,
            String lastName,
            String email,
            String phone,
            String gender,
            String category,
            String password
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("email", email);
        values.put("phone", phone);
        values.put("gender", gender);
        values.put("category", category);
        values.put("password", password);

        long result = db.insert("users", null, values);

        return result != -1;
    }

    public boolean checkUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

    public boolean emailExists(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=?",
                new String[]{email}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

    public boolean insertTrip(
            int id,
            String destination,
            String country,
            int durationDays,
            double price,
            double rating,
            String description,
            String image
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("destination", destination);
        values.put("country", country);
        values.put("duration_days", durationDays);
        values.put("price", price);
        values.put("rating", rating);
        values.put("description", description);
        values.put("image", image);

        long result = db.insert("trips", null, values);

        return result != -1;
    }

    public boolean tripExists(int tripId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM trips WHERE id=?",
                new String[]{String.valueOf(tripId)}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

    public Cursor getAllUsers() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, first_name, last_name, email, phone FROM users WHERE category != ?",
                new String[]{"Admin"}
        );
    }

    public boolean deleteUser(int userId) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "users",
                "id=?",
                new String[]{String.valueOf(userId)}
        );

        return result > 0;
    }

    public boolean insertReservation(
            String tripName,
            int quantity,
            String reservationType,
            String reservationDate
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trip_name", tripName);
        values.put("quantity", quantity);
        values.put("reservation_type", reservationType);
        values.put("reservation_date", reservationDate);

        long result = db.insert("reservations", null, values);

        return result != -1;
    }

    public Cursor getAllReservations() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, trip_name, quantity, reservation_type, reservation_date FROM reservations",
                null
        );
    }

    public Cursor getAllTrips() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, destination, country, duration_days, price, rating, description, image FROM trips",
                null
        );
    }

    public boolean updateTrip(
            int id,
            String destination,
            String country,
            int durationDays,
            double price,
            double rating,
            String description,
            String image
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("destination", destination);
        values.put("country", country);
        values.put("duration_days", durationDays);
        values.put("price", price);
        values.put("rating", rating);
        values.put("description", description);
        values.put("image", image);

        int result = db.update(
                "trips",
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    public boolean deleteTrip(int tripId) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                "favorites",
                "id=?",
                new String[]{String.valueOf(tripId)}
        );

        int result = db.delete(
                "trips",
                "id=?",
                new String[]{String.valueOf(tripId)}
        );

        return result > 0;
    }

    public boolean insertAdmin(
            String name,
            String email,
            String phone,
            String password
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("first_name", name);
        values.put("last_name", "");
        values.put("email", email);
        values.put("phone", phone);
        values.put("gender", "Male");
        values.put("category", "Admin");
        values.put("password", password);

        long result = db.insert("users", null, values);

        return result != -1;
    }

    public String getUserCategory(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT category FROM users WHERE email=?",
                new String[]{email}
        );

        String category = "";

        if (cursor.moveToFirst()) {
            category = cursor.getString(0);
        }

        cursor.close();

        return category;
    }

    public boolean insertFavorite(
            int id,
            String destination,
            String country,
            int durationDays,
            double price,
            double rating,
            String description,
            String image
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("destination", destination);
        values.put("country", country);
        values.put("duration_days", durationDays);
        values.put("price", price);
        values.put("rating", rating);
        values.put("description", description);
        values.put("image", image);

        long result = db.insert("favorites", null, values);

        return result != -1;
    }

    public Cursor getAllFavorites() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, destination, country, duration_days, price, rating, description, image FROM favorites",
                null
        );
    }

    public boolean deleteFavorite(int tripId) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "favorites",
                "id=?",
                new String[]{String.valueOf(tripId)}
        );

        return result > 0;
    }
    public boolean updateUserProfile(
            String email,
            String firstName,
            String lastName,
            String phone,
            String password
    ) {
        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("phone", phone);

        if (password != null && !password.isEmpty()) {
            values.put("password", password);
        }

        int result =
                db.update(
                        "users",
                        values,
                        "email=?",
                        new String[]{email}
                );

        return result > 0;
    }
}