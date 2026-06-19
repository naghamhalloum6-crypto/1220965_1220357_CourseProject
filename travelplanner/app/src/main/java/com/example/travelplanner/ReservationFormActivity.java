package com.example.travelplanner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationFormActivity extends AppCompatActivity {

    private EditText etQuantity;
    private Spinner spReservationType;
    private Button btnConfirmReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        etQuantity = findViewById(R.id.etQuantity);
        spReservationType = findViewById(R.id.spReservationType);
        btnConfirmReservation = findViewById(R.id.btnConfirmReservation);

        setupReservationTypes();

        // Confirm reservation after validating input
        btnConfirmReservation.setOnClickListener(v ->
                confirmReservation()
        );
    }

    private void setupReservationTypes() {

        String[] types = {
                "Standard",
                "Family",
                "Group"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        types
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spReservationType.setAdapter(adapter);
    }

    private void confirmReservation() {

        String quantityText =
                etQuantity.getText()
                        .toString()
                        .trim();

        if (quantityText.isEmpty()) {

            etQuantity.setError("Quantity is required");
            return;
        }

        int quantity =
                Integer.parseInt(quantityText);

        if (quantity <= 0) {

            etQuantity.setError("Quantity must be greater than zero");
            return;
        }

        Toast.makeText(
                this,
                "Reservation confirmed",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}