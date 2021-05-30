package com.mahmoudjoe3.wasfa.pojo;

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
}
