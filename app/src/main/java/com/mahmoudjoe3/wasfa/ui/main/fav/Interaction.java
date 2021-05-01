package com.mahmoudjoe3.wasfa.ui.main.fav;

public class Interaction {
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
