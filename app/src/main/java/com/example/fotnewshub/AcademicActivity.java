package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AcademicActivity extends AppCompatActivity {

    ImageView backToHome2,profileIcon3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);

        backToHome2 = findViewById(R.id.backToHome2);
        profileIcon3 = findViewById(R.id.profileIcon3);

        backToHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademicActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
