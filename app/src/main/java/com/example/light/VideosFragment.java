package com.example.light;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.MediaStore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment {
    private static final String TAG = "VideosFragment";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<VideoItem> videoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        
        // Initialize adapter with empty list
        videoAdapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(videoAdapter);
        
        checkPermissionsAndLoadVideos();
        
        return view;
    }

    private void checkPermissionsAndLoadVideos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above, we need to handle media access differently
            loadVideos();
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Requesting storage permission");
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                Log.d(TAG, "Permission already granted, loading videos");
                loadVideos();
            }
        }
    }

    @SuppressLint("Range")
    private void loadVideos() {
        // Get all videos using MediaStore
        String[] projection = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.RELATIVE_PATH,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION
        };

        String selection = MediaStore.Video.Media.SIZE + " > 0";
        
        android.content.ContentResolver contentResolver = requireContext().getContentResolver();
        android.database.Cursor cursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        );

        if (cursor == null) {
            Log.e(TAG, "Cursor is null");
            return;
        }

        try {
            int count = cursor.getCount();
            Log.d(TAG, "Found " + count + " videos");
            
            if (count > 0) {
                videoList.clear(); // Clear existing list
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    String relativePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.RELATIVE_PATH));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    
                    // Get the content URI for the video
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    String contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString() + '/' + id;
                    
                    Log.d(TAG, "Found video: " + name + 
                        " Size: " + size + " bytes" + 
                        " Duration: " + duration + " ms" + 
                        " Path: " + relativePath);
                    
                    // Add video to list
                    videoList.add(new VideoItem(name, contentUri, contentUri));
                    Log.d(TAG, "Added video: " + name);
                }
                videoAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading videos", e);
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission granted, loading videos");
            loadVideos();
        } else {
            Log.w(TAG, "Permission denied");
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
