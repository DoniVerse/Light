package com.example.light;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private final List<VideoItem> videoList;

    public VideoAdapter(List<VideoItem> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem video = videoList.get(position);
        holder.title.setText(video.getName());
        
        // Load thumbnail using Glide
        Glide.with(holder.itemView.getContext())
            .load(video.getThumbnailPath())
            .into(holder.thumbnail);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            // Launch VideoPlayerActivity with video URI and list
            Intent intent = new Intent(holder.itemView.getContext(), VideoPlayerActivity.class);
            intent.setData(Uri.parse(video.getPath()));
            intent.putParcelableArrayListExtra("video_list", (ArrayList<VideoItem>) videoList);
            intent.putExtra("position", position);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final TextView title;

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
        }
    }
}
