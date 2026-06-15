package com.example.travelplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "travel_planner.db";
    private static final int DATABASE_VERSION = 1;

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

        ContentValues admin = new ContentValues();

        admin.put("first_name", "Admin");
        admin.put("last_name", "User");
        admin.put("email", "admin@admin.com");
        admin.put("phone", "0000000000");
        admin.put("gender", "Male");
        admin.put("category", "Admin");
        admin.put("password", "Admin123!");

        db.insert("users", null, admin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");

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
}