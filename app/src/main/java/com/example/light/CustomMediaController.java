package com.example.light;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;

public class CustomMediaController extends MediaController {
    private final VideoPlayerListener listener;

    public interface VideoPlayerListener {
        void onPlayNextVideo();
        void onPlayPreviousVideo();
    }

    public CustomMediaController(Context context, VideoPlayerListener listener) {
        super(context);
        this.listener = listener;
        initCustomButtons();
    }

    private void initCustomButtons() {
        View mediaController = LayoutInflater.from(getContext()).inflate(R.layout.custom_media_controller, null);
        setAnchorView(mediaController);
        
        // Find and set up next button
        ImageButton nextButton = mediaController.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(v -> listener.onPlayNextVideo());
        
        // Find and set up previous button
        ImageButton previousButton = mediaController.findViewById(R.id.btn_previous);
        previousButton.setOnClickListener(v -> listener.onPlayPreviousVideo());
    }
}
