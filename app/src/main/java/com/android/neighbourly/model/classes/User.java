package com.android.neighbourly.model.classes;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.Objects;

public class User {
    private String id;
    private String name;
    private String mobile;
    private String email;
    @Exclude
    private Uri imageUri;

    public User() {
    }

    public User(String name, String mobile, String email, Uri imageUri) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public Uri getImageUri() {
        return imageUri;
    }

    @Exclude
    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(mobile, user.mobile) && Objects.equals(email, user.email);
    }
}
