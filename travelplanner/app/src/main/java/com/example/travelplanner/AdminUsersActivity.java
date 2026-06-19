package com.example.travelplanner;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminUsersActivity extends AppCompatActivity {

    private ListView listUsers;
    private DatabaseHelper databaseHelper;

    private ArrayList<String> usersList;
    private ArrayList<Integer> userIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        listUsers = findViewById(R.id.listUsers);
        databaseHelper = new DatabaseHelper(this);

        loadUsers();

        // Delete user when admin clicks on a user
        listUsers.setOnItemClickListener((parent, view, position, id) -> {
            int userId = userIds.get(position);
            confirmDeleteUser(userId);
        });
    }

    private void loadUsers() {

        usersList = new ArrayList<>();
        userIds = new ArrayList<>();

        Cursor cursor = databaseHelper.getAllUsers();

        if (cursor.getCount() == 0) {
            usersList.add("No users found");
        } else {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String phone = cursor.getString(4);

                userIds.add(id);

                usersList.add(
                        firstName + " " + lastName +
                                "\nEmail: " + email +
                                "\nPhone: " + phone
                );
            }
        }

        cursor.close();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        usersList
                );

        listUsers.setAdapter(adapter);
    }

    private void confirmDeleteUser(int userId) {

        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Delete", (dialog, which) -> {

                    boolean deleted =
                            databaseHelper.deleteUser(userId);

                    if (deleted) {
                        Toast.makeText(
                                this,
                                "user deleted",
                                Toast.LENGTH_SHORT
                        ).show();

                        loadUsers();
                    } else {
                        Toast.makeText(
                                this,
                                "failed to delete user",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}