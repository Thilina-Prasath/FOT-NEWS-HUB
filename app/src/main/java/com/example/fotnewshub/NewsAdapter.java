package fotnewshub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fotnewshub.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<fotnewshub.NewsPostItem> newsPostItemList;
    private Context context;

    public NewsAdapter(List<fotnewshub.NewsPostItem> newsPostItemList, Context context) {
        this.newsPostItemList = newsPostItemList;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for the news post
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_post, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        // Get the current item
        fotnewshub.NewsPostItem newsPostItem = newsPostItemList.get(position);

        // Bind data to the UI elements
        holder.headerText.setText(newsPostItem.getHeaderText());
        holder.subHeaderText.setText(newsPostItem.getSubHeaderText());
        holder.titleText.setText(newsPostItem.getTitleText());
        holder.subtitleText.setText(newsPostItem.getSubtitleText());
        holder.descriptionText.setText(newsPostItem.getDescriptionText());

        // Load images using Glide
        Glide.with(context).load(newsPostItem.getProfileImage()).into(holder.profileImage);
        Glide.with(context).load(newsPostItem.getImageUrl()).into(holder.imageContent);
    }

    @Override
    public int getItemCount() {
        return newsPostItemList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        // Declare all the UI elements that will be bound
        TextView headerText, subHeaderText, titleText, subtitleText, descriptionText;
        ImageView profileImage, imageContent;

        public NewsViewHolder(View itemView) {
            super(itemView);
            // Initialize the UI elements
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
