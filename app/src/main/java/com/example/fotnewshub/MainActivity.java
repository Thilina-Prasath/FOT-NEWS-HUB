package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast; // This might become optional if you remove all toasts

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fotnewshub.NewsPostAdapter;
import fotnewshub.NewsPostItem;
import fotnewshub.SportActivity; // Import SportActivity


public class MainActivity extends AppCompatActivity {

    ImageView backArrow, sports, academic, event, profileIcon1, info;
    RecyclerView myRecyclerView;
    NewsPostAdapter newsPostAdapter;
    List<NewsPostItem> newsPostItemList;

    private DatabaseReference sportNewsRef;
    private DatabaseReference academicNewsRef;
    private DatabaseReference eventNewsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backArrow = findViewById(R.id.backArrow);
        sports = findViewById(R.id.sports);
        academic = findViewById(R.id.academic);
        event = findViewById(R.id.event);
        profileIcon1 = findViewById(R.id.profileIcon1);
        info = findViewById(R.id.info);
        myRecyclerView = findViewById(R.id.myrecycleview);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsPostItemList = new ArrayList<>();
        newsPostAdapter = new NewsPostAdapter(newsPostItemList, this);
        myRecyclerView.setAdapter(newsPostAdapter);

        sportNewsRef = FirebaseDatabase.getInstance().getReference("sportNews");
        academicNewsRef = FirebaseDatabase.getInstance().getReference("academicNews");
        eventNewsRef = FirebaseDatabase.getInstance().getReference("eventNews");

        fetchAllNewsData();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SportActivity.class);
                startActivity(intent);
            }
        });

        academic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AcademicActivity.class);
                startActivity(intent);
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intent);
                // No finish() here if you want to keep MainActivity in the back stack
            }
        });

        profileIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DevinfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchAllNewsData() {
        newsPostItemList.clear();

        sportNewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewsPostItem newsPostItem = snapshot.getValue(NewsPostItem.class);
                    if (newsPostItem != null) {
                        newsPostItemList.add(newsPostItem);
                    }
                }
                fetchAcademicNews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load sports news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAcademicNews() {
        academicNewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewsPostItem newsPostItem = snapshot.getValue(NewsPostItem.class);
                    if (newsPostItem != null) {
                        newsPostItemList.add(newsPostItem);
                    }
                }
                // Fetch Event News after Academic News
                fetchEventNews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load academic news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEventNews() {
        eventNewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewsPostItem newsPostItem = snapshot.getValue(NewsPostItem.class);
                    if (newsPostItem != null) {
                        newsPostItemList.add(newsPostItem);
                    }
                }
                newsPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load event news", Toast.LENGTH_SHORT).show();
            }
        });
    }
}