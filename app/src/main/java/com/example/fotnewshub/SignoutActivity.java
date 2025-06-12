package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignoutActivity extends AppCompatActivity {

    ImageView backToUserprofile1;
    Button okButton1,cancelButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signout);

        backToUserprofile1 = findViewById(R.id.backToUserprofile1);
        okButton1 = findViewById(R.id.okButton1);
        cancelButton1 = findViewById(R.id.cancelButton1);

        backToUserprofile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignoutActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        okButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignoutActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancelButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignoutActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}