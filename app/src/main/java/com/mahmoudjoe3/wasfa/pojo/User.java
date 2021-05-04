package com.mahmoudjoe3.wasfa.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private long createdDate;
    private String name, email, password,phone, imageUrl, bio, nationality, gender;
    private List<String> links;
    private List<Following> followings;
    private List<Recipe> recipes;
    private int follower;

    public User() {
    }

    public User(int id, String name, String password, String imageUrl, String bio, String nationality) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.bio = bio;
        this.nationality = nationality;
        createdDate=System.currentTimeMillis();
        links=new ArrayList<>();
        followings=new ArrayList<>();
        recipes=new ArrayList<>();
        follower=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<Following> getFollowing() {
        return followings;
    }

    public void setFollowing(List<Integer> following) {
        this.followings = followings;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFollowings(List<Following> followings) {
        this.followings = followings;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Following> getFollowings() {
        return followings;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
