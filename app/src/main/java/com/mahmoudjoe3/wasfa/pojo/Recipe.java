package com.mahmoudjoe3.wasfa.pojo;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private int userId;
    private String userName;
    private String userProfileThumbnail;
    private String title;
    private String description;
    private String nationality;
    private Bitmap flag;
    private long postTime;
    private String prepareTime;
    private List<String> categories;
    private List<String> ingredients;
    private List<String> steps;
    private int numberOfLike;
    private int numberOfShare;
    private List<String> imgUrls;
    private List<Comment> comments;


    public Recipe() {
    }

    public Recipe(int id, int userId, String userName, String userProfileThumbnail, String title,
                  String description, long postTime, String prepareTime, List<String> categories,
                  List<String> ingredients, List<String> steps, int numberOfLike,
                  int numberOfShare, List<String> imgUrls) {
        this.id=id;
        this.userId = userId;
        this.userName = userName;
        this.userProfileThumbnail = userProfileThumbnail;
        this.title = title;
        this.description = description;
        this.postTime = postTime;
        this.prepareTime = prepareTime;
        this.categories = categories;
        this.ingredients = ingredients;
        this.steps = steps;
        this.numberOfLike = numberOfLike;
        this.numberOfShare = numberOfShare;
        this.imgUrls = imgUrls;
        this.comments=new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getFlag() {
        return flag;
    }

    public void setFlag(Bitmap flag) {
        this.flag = flag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileThumbnail() {
        return userProfileThumbnail;
    }

    public void setUserProfileThumbnail(String userProfileThumbnail) {
        this.userProfileThumbnail = userProfileThumbnail;
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

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public int getNumberOfLike() {
        return numberOfLike;
    }

    public void setNumberOfLike(int numberOfLike) {
        this.numberOfLike = numberOfLike;
    }


    public int getNumberOfShare() {
        return numberOfShare;
    }

    public void setNumberOfShare(int numberOfShare) {
        this.numberOfShare = numberOfShare;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
