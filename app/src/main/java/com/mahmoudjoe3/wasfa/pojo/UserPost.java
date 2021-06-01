package com.mahmoudjoe3.wasfa.pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPost {

    /**
     * Id : 0
     * Name : string
     * Email : string
     * Password : string
     * Gender : string
     * ImageUrl : string
     * Phone : string
     * Bio : string
     * Nationality : string
     * CreatedDate : 2021-05-30T11:28:50.467Z
     * FollowersCount : 0
     */

    private int Id;
    private String Name;
    private String Email;
    private String Password;
    private String Gender;
    private String ImageUrl;
    private String Phone;
    private String Bio;
    private String Nationality;
    private String CreatedDate;
    private int FollowersCount;
    private List<Following> followings;
    private List<String> links;




    public UserPost(final int id, final String name, final String email, final String password, final String gender, final String phone, final String nationality) {
        this.Id = id;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Gender = gender;
        this.ImageUrl = null;
        this.Phone = phone;
        this.Bio = "";
        this.Nationality = nationality;
        this.CreatedDate = ""+System.currentTimeMillis();
        this.FollowersCount = 0;
        links=new ArrayList<>(Arrays.asList("","",""));

    }

    public static UserPost parseUserRespone(String userJsonString) {
        try {
            JSONObject jsonObject = new JSONObject(userJsonString);
            JSONObject userObject = jsonObject.getJSONArray("Users").getJSONObject(0);

            int id = userObject.getInt("Id");
            String name = userObject.getString("Name");
            String email = userObject.getString("Email");
            String password = userObject.getString("Password");
            String gender = userObject.getString("Gender");
            String imageUrl = userObject.getString("ImageUrl");
            String phone = userObject.getString("Phone");
            String bio = userObject.getString("Bio");
            String nationality = userObject.getString("Nationality");
            long createdDate = userObject.getLong("CreatedDate");
            int followersCount = userObject.getInt("FollowersCount");
            List<String> links = new ArrayList<>();
            List<Following> followingList = new ArrayList<>();

            JSONArray linksArray = userObject.getJSONArray("Links");
            JSONArray followings = userObject.getJSONArray("Followings");

            for (int i = 0; i < followings.length(); i++) {
                JSONObject obj = followings.getJSONObject(i);
                int followingId = obj.getInt("FollowingId");
                String followingName = obj.getString("FollowingName");
                String followingImg = obj.getString("FollowingImage");
                followingList.add(new Following(followingId, followingName, followingImg));
            }

            for (int i = 0; i < linksArray.length(); i++) {
                JSONObject obj = linksArray.getJSONObject(i);
                links.add(obj.getString("Link"));
            }
            //    public UserPost(final int id, final String name, final String email, final String password, final String gender, final String phone, final String nationality) {
            UserPost u=new UserPost(id, name, email, password, gender,phone,nationality);
            u.setBio(bio);
            u.setCreatedDate(String.valueOf(createdDate));
            u.setFollowersCount(followersCount);
            u.setFollowings(followingList);
            u.setImageUrl(imageUrl);
            u.setLinks(links);
            return u;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String Bio) {
        this.Bio = Bio;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getFollowersCount() {
        return FollowersCount;
    }

    public void setFollowersCount(int FollowersCount) {
        this.FollowersCount = FollowersCount;
    }

    public List<Following> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Following> followings) {
        this.followings = followings;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
