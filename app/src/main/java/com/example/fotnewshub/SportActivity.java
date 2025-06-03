package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SportActivity extends AppCompatActivity {

    ImageView backToHome,profileIcon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        backToHome = findViewById(R.id.backToHome);
        profileIcon2 = findViewById(R.id.profileIcon2);

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SportActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SportActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
