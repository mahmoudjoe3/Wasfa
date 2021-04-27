package com.mahmoudjoe3.wasfa.pojo;

public class Comment {
    private int id;
    private int userId;
    private int recipeId;
    private String username;
    private String userImageUrl;
    private boolean creator;
    private long createdTime;
    private String commentText;
    private int loveCount;

    public Comment(int recipeId,String username, String userImageUrl, boolean creator, String commentText) {
        this.recipeId=recipeId;
        this.username = username;
        this.userImageUrl = userImageUrl;
        this.creator = creator;
        this.commentText = commentText;
        createdTime=System.currentTimeMillis();
        loveCount=0;
    }

    public Comment() {
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }
}
