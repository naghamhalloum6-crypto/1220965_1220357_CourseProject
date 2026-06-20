package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imgLogo = findViewById(R.id.imgLogo);
        TextView txtAppName = findViewById(R.id.txtAppName);
        TextView txtSubtitle = findViewById(R.id.txtSubtitle);

// Load fade animation from res/anim
        Animation fadeIn =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.fade_in
                );
        Animation scale =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.scale
                );

        imgLogo.startAnimation(scale);
        txtAppName.startAnimation(fadeIn);
        txtSubtitle.startAnimation(fadeIn);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, IntroductionActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}