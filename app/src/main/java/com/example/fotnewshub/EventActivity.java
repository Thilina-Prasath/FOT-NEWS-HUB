package com.example.fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fotnewshub.NewsPostAdapter;
import fotnewshub.NewsPostItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private ImageView backToHome, profileIcon;
    private RecyclerView recyclerView;
    private NewsPostAdapter newsPostAdapter;
    private List<NewsPostItem> newsPostItemList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);  // Ensure the correct layout name

        // Initialize views
        backToHome = findViewById(R.id.backToHome);
        profileIcon = findViewById(R.id.profileIcon2);  // Use the correct ID from your XML
        recyclerView = findViewById(R.id.newsRecyclerView);  // Make sure this matches your XML ID

        // Set up RecyclerView for displaying news posts
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsPostItemList = new ArrayList<>();
        newsPostAdapter = new NewsPostAdapter(newsPostItemList, this);
        recyclerView.setAdapter(newsPostAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("eventNews");

        fetchEventNewsData();

        backToHome.setOnClickListener(v -> {
            Intent intent = new Intent(EventActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Close the current activity
        });

        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EventActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();  // Close the current activity
        });
    }

    private void fetchEventNewsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsPostItemList.clear();
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
                Toast.makeText(EventActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}