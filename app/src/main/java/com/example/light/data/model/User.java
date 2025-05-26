package com.example.light.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    
    private String password;
    private String username;
    private String createdAt;
    private boolean isActive;

    public User(@NonNull String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.createdAt = java.time.LocalDateTime.now().toString();
        this.isActive = true;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
