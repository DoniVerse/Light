package com.example.light;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class VideoPlayerActivity extends AppCompatActivity implements CustomMediaController.VideoPlayerListener {
    private VideoView videoView;
    private CustomMediaController mediaController;
    private List<VideoItem> videoList;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);
        
        // Get video data from intent
        Intent intent = getIntent();
        if (intent != null) {
            videoList = intent.getParcelableArrayListExtra("video_list");
            currentPosition = intent.getIntExtra("position", 0);

            if (videoList != null && !videoList.isEmpty()) {
                // Get the current video URI from the list
                Uri videoUri = Uri.parse(videoList.get(currentPosition).getPath());
                
                // Initialize custom media controller
                mediaController = new CustomMediaController(this, this);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(videoUri);
                videoView.start();
            }
        }
    }

    @Override
    public void onPlayNextVideo() {
        if (videoList == null || videoList.isEmpty()) return;
        
        // Get the next video in the list
        int nextPosition = (currentPosition + 1) % videoList.size();
        VideoItem nextVideo = videoList.get(nextPosition);
        
        // Set the new video
        videoView.setVideoURI(Uri.parse(nextVideo.getPath()));
        videoView.start();
        
        // Update the current position
        currentPosition = nextPosition;
    }

    @Override
    public void onPlayPreviousVideo() {
        if (videoList == null || videoList.isEmpty()) return;
        
        // Get the previous video in the list
        int previousPosition = (currentPosition - 1 + videoList.size()) % videoList.size();
        VideoItem previousVideo = videoList.get(previousPosition);
        
        // Set the new video
        videoView.setVideoURI(Uri.parse(previousVideo.getPath()));
        videoView.start();
        
        // Update the current position
        currentPosition = previousPosition;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.start();
        }
    }
}
