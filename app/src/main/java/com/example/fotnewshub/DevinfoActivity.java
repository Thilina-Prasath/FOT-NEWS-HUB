package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DevinfoActivity extends AppCompatActivity {

    ImageView backToHomeforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_devinfo);

        backToHomeforgot = findViewById(R.id.backToHomeforgot);

        backToHomeforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DevinfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}