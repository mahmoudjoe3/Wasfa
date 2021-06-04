package com.mahmoudjoe3.wasfaty.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "interactions_table")
public class Interaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String interactWith;
    private String imageUrl;
    private String interaction;
    private long dateTime;

    public Interaction(String interactWith, String imageUrl, String interaction) {
        this.interactWith = interactWith;
        this.imageUrl = imageUrl;
        this.interaction= interaction;
        dateTime = System.currentTimeMillis();
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

    public long getDateTime() {
        return dateTime;
    }

    public void setInteractWith(String interactWith) {
        this.interactWith = interactWith;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
