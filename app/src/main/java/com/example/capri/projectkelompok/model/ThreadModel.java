package com.example.capri.projectkelompok.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by capri on 5/19/2017.
 */
@IgnoreExtraProperties
public class ThreadModel {
    private String title;
    private String description;
    private String image;
    private String username;

    public ThreadModel(){

    }

    public ThreadModel(String title, String description, String image, String username) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ThreadModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
