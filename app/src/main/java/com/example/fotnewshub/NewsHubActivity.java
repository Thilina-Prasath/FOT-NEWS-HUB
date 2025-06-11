package fotnewshub;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fotnewshub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsHubActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private fotnewshub.NewsPostAdapter newsPostAdapter;
    private List<fotnewshub.NewsPostItem> newsPostList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_hub);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the newsPostList and adapter
        newsPostList = new ArrayList<>();
        newsPostAdapter = new fotnewshub.NewsPostAdapter(newsPostList, this); // Pass 'this' to the adapter to provide context
        recyclerView.setAdapter(newsPostAdapter);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("newsPosts");

        // Fetch news data from Firebase
        fetchNewsPostsData();
    }

    private void fetchNewsPostsData() {
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
                Toast.makeText(NewsHubActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
