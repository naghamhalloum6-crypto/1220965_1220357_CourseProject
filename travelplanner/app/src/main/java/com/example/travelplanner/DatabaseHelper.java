package com.example.travelplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "travel_planner.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create users table

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

        // create trips table

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

        // insert default admin

        ContentValues admin = new ContentValues();

        admin.put("first_name", "Admin");
        admin.put("last_name", "User");
        admin.put("email", "admin@admin.com");
        admin.put("phone", "0000000000");
        admin.put("gender", "Male");
        admin.put("category", "Admin");

        // save admin password as hash

        admin.put(
                "password",
                PasswordHelper.hashPassword("Admin123!")
        );

        db.insert("users", null, admin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS trips");

        onCreate(db);
    }

    // insert new user

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

    // check login data

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

    // check if email exists

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

    // insert trip into database

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

    // check if trip already exists

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
    // Get all normal users
    public Cursor getAllUsers() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, first_name, last_name, email, phone FROM users WHERE category != ?",
                new String[]{"Admin"}
        );
    }

    // Delete user by id
    public boolean deleteUser(int userId) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "users",
                "id=?",
                new String[]{String.valueOf(userId)}
        );

        return result > 0;
    }
}