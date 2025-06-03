package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    ImageView backToHome3,profileIcon4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        backToHome3 = findViewById(R.id.backToHome3);
        profileIcon4 = findViewById(R.id.profileIcon4);

        backToHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
