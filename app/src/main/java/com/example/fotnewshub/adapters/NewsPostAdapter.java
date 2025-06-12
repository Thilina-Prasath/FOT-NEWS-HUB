package fotnewshub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fotnewshub.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private List<fotnewshub.NewsPostItem> newsPostItemList;
    private Context context;

    public NewsPostAdapter(List<fotnewshub.NewsPostItem> newsPostItemList, Context context) {
        this.newsPostItemList = newsPostItemList;
        this.context = context;
    }

    @Override
    public NewsPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_post, parent, false);
        return new NewsPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsPostViewHolder holder, int position) {
        fotnewshub.NewsPostItem newsPostItem = newsPostItemList.get(position);

        Log.d("NewsPostAdapter", "Profile Image URL: " + newsPostItem.getProfileImage());
        Log.d("NewsPostAdapter", "Content Image URL: " + newsPostItem.getImageUrl());

        holder.headerText.setText(newsPostItem.getHeaderText());
        holder.subHeaderText.setText(newsPostItem.getSubHeaderText());
        holder.titleText.setText(newsPostItem.getTitleText());
        holder.subtitleText.setText(newsPostItem.getSubtitleText());
        holder.descriptionText.setText(newsPostItem.getDescriptionText());

        String profileImageUrl = newsPostItem.getProfileImage();
        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(context)
                    .load(profileImageUrl)
                    .into(holder.profileImage);
        }

        String contentImageUrl = newsPostItem.getImageUrl();
        if (contentImageUrl != null && !contentImageUrl.isEmpty()) {
            try {
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(contentImageUrl);

                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.d("NewsPostAdapter", "Content Image URL (Download): " + uri.toString());

                    Glide.with(context).load(uri).into(holder.imageContent);
                }).addOnFailureListener(exception -> {
                    Log.e("NewsPostAdapter", "Error loading content image", exception);
                    Glide.with(context)
                            .load(android.R.drawable.ic_menu_gallery)
                            .into(holder.imageContent);
                });
            } catch (Exception e) {
                Log.e("NewsPostAdapter", "Error loading Firebase image URL", e);
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsPostItemList.size();
    }

    public static class NewsPostViewHolder extends RecyclerView.ViewHolder {
        TextView headerText, subHeaderText, titleText, subtitleText, descriptionText;
        ImageView profileImage, imageContent;

        public NewsPostViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            imageContent = itemView.findViewById(R.id.imageContent);
            headerText = itemView.findViewById(R.id.headerText);
            subHeaderText = itemView.findViewById(R.id.subHeaderText);
            titleText = itemView.findViewById(R.id.titleText);
            subtitleText = itemView.findViewById(R.id.subtitleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }
    }
}