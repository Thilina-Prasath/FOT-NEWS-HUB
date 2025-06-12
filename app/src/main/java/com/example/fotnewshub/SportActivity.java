package fotnewshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fotnewshub.MainActivity;
import com.example.fotnewshub.R;
import com.example.fotnewshub.UserProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SportActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private fotnewshub.NewsPostAdapter newsPostAdapter;
    private List<fotnewshub.NewsPostItem> newsPostList;
    private DatabaseReference databaseReference;

    private ImageView backToHome, profileIcon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        // Initialize RecyclerView and other views
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        backToHome = findViewById(R.id.backToHome);
        profileIcon2 = findViewById(R.id.profileIcon2);

        // Set up RecyclerView
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsPostList = new ArrayList<>();
        newsPostAdapter = new fotnewshub.NewsPostAdapter(newsPostList, this);
        newsRecyclerView.setAdapter(newsPostAdapter);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("sportNews");

        // Fetch news data from Firebase
        fetchNewsPostsData();

        // Back button click listener
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SportActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the activity when going back
            }
        });

        // Profile icon click listener (navigate to profile screen)
        profileIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SportActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();  // Close the activity when going to profile
            }
        });
    }

    private void fetchNewsPostsData() {
        // Fetch data from Firebase (sports news)
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsPostList.clear();  // Clear previous data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    fotnewshub.NewsPostItem newsPostItem = snapshot.getValue(fotnewshub.NewsPostItem.class);
                    if (newsPostItem != null) {
                        newsPostList.add(newsPostItem);  // Add the fetched item to the list
                    }
                }
                newsPostAdapter.notifyDataSetChanged();  // Notify the adapter that data has been updated
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SportActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
