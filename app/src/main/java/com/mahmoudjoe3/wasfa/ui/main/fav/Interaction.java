package com.mahmoudjoe3.wasfa.ui.main.fav;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "interactions_table")
public class Interaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String interactWith;
    private String imageUrl;
    private String interaction;
    private String dateTime;

    public Interaction(String interactWith, String imageUrl, String interaction, String dateTime) {
        this.interactWith = interactWith;
        this.imageUrl = imageUrl;
        this.interaction= interaction;
        this.dateTime = dateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getInteractWith() {
        return interactWith;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInteraction() {
        return interaction;
    }

    public String getDateTime() {
        return dateTime;
    }
}
