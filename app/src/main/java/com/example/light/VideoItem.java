package com.example.light;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoItem implements Parcelable {
    private String name;
    private String path;
    private String thumbnailPath;

    public VideoItem(String name, String path, String thumbnailPath) {
        this.name = name;
        this.path = path;
        this.thumbnailPath = thumbnailPath;
    }

    // Required for Parcelable
    protected VideoItem(Parcel in) {
        name = in.readString();
        path = in.readString();
        thumbnailPath = in.readString();
    }

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(thumbnailPath);
    }
}
