package com.example.travelplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactUsActivity extends AppCompatActivity {

    private Button btnCall;
    private Button btnLocate;
    private Button btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);

        btnCall = findViewById(R.id.btnCall);
        btnLocate = findViewById(R.id.btnLocate);
        btnEmail = findViewById(R.id.btnEmail);

        // Open phone dialer
        btnCall.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:022981999")
                    );

            startActivity(intent);
        });

        // Open map location
        btnLocate.setOnClickListener(v -> {
            Uri location =
                    Uri.parse("geo:31.9616,35.1906?q=Birzeit University");

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            location
                    );

            startActivity(intent);
        });

        // Open email application
        btnEmail.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            Intent.ACTION_SENDTO,
                            Uri.parse("mailto:support@travelplanner.com")
                    );

            intent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Travel Planner Support"
            );

            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }
}