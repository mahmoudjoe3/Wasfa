package com.mahmoudjoe3.wasfaty.pojo;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {
    private int Id;
    private int UserId;
    private String userName;
    private String userProfileThumbnail;
    private String Title;
    private String Description;
    private String Nationality;
    private long CreatedDate;
    private String PrepareTime;
    private List<String> categories;
    private List<String> Ingredients;
    private List<String> Steps;
    private int LoveCount;
    private int ShareCount;
    private List<String> imgUrls;
    private List<Comment> comments;
    private String Privacy;
    List<Uri> imageUris;



    public Recipe() {
    }

    public Recipe(int id, int userId, String userName, String userProfileThumbnail, String title,
                  String description, String nationality, long CreatedDate, String prepareTime, List<String> categories,
                  List<String> ingredients, List<String> steps, int LoveCount,
                  int ShareCount, List<String> imgUrls) {
        this.Id =id;
        this.UserId = userId;
        this.userName = userName;
        this.userProfileThumbnail = userProfileThumbnail;
        this.Title = title;
        this.Description = description;
        this.Nationality = nationality;
        this.CreatedDate = CreatedDate;
        this.PrepareTime = prepareTime;
        this.categories = categories;
        this.Ingredients = ingredients;
        this.Steps = steps;
        this.LoveCount = LoveCount;
        this.ShareCount = ShareCount;
        this.imgUrls = imgUrls;
        this.comments=new ArrayList<>();
    }


    public static List<Recipe> parseRecipeJson(String jsonResponse) {
        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray recipeArray = jsonObject.getJSONArray("Recipe");

            for(int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipe = recipeArray.getJSONObject(i);
                int id = recipe.getInt("Id");
                String title = recipe.getString("Title");
                String description = recipe.getString("Description");
                String ingredients = recipe.getString("Ingredients");
                List<String> ingredientList = splitString(ingredients);
                String steps = recipe.getString("Steps");
                List<String> stepList = splitString(steps);
                String nationality = recipe.getString("Nationality");
                long createdDate = recipe.getLong("CreatedDate");
                String prepareTime = recipe.getString("PrepareTime");
                String userName = recipe.getString("UserName");
                String userImageUrl = recipe.getString("UserImageUrl");
                int loveCount = recipe.getInt("LoveCount");
                int shareCount = recipe.getInt("ShareCount");
                int userId = recipe.getInt("UserId");
                List<String> links = new ArrayList<>();
                List<Comment> commentList = new ArrayList<>();
                JSONArray commentArray = recipe.getJSONArray("Reviews");

                for (int j = 0; j < commentArray.length(); j++) {
                    JSONObject obj = commentArray.getJSONObject(j);
                    int reviewId = obj.getInt("Id");
                    int reviewUserId = obj.getInt("UserId");
                    String reviewUserName = obj.getString("UserName");
                    String reviewUserImg = obj.getString("UserImage");
                    String reviewText = obj.getString("ReviewText");
                    float reviewResult = Float.parseFloat(obj.getString("ReviewResult"));
                    boolean isCreator = obj.getBoolean("IsCreator");
                    long reviewCreatedDate = obj.getLong("CreatedData");
                    commentList.add(new Comment(reviewId, reviewUserId, reviewUserName, reviewUserImg, isCreator, reviewCreatedDate, reviewText, reviewResult));
                }

                JSONArray categoryArray = recipe.getJSONArray("Categories");
                List<String> categoryList = new ArrayList<>();
                for (int j = 0; j < categoryArray.length(); j++) {
                    categoryList.add(categoryArray.getJSONObject(j).getString("Title"));
                }

                JSONArray imgArray = recipe.getJSONArray("ImagesUrls");
                List<String> imgList = new ArrayList<>();
                for (int j = 0; j < imgArray.length(); j++) {
                    imgList.add(imgArray.getString(j));
                }

                Recipe r = new Recipe(id, userId, userName,userImageUrl, title, description,nationality,createdDate,prepareTime, categoryList,ingredientList,stepList,loveCount,shareCount,imgList);
                recipeList.add(r);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    private static List<String> splitString(String string) {
        return new ArrayList<String>(Arrays.asList(string.split("~")));
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
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
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public long getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(long createdDate) {
        this.CreatedDate = createdDate;
    }

    public String getPrepareTime() {
        return PrepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.PrepareTime = prepareTime;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.Ingredients = ingredients;
    }

    public List<String> getSteps() {
        return Steps;
    }

    public void setSteps(List<String> steps) {
        this.Steps = steps;
    }

    public int getLoveCount() {
        return LoveCount;
    }

    public void setLoveCount(int loveCount) {
        this.LoveCount = loveCount;
    }


    public int getShareCount() {
        return ShareCount;
    }

    public void setShareCount(int shareCount) {
        this.ShareCount = shareCount;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        this.Nationality = nationality;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(String privacy) {
        Privacy = privacy;
    }


    public void setImgUris(List<Uri> imageUrls) {
        imageUris=imageUrls;
    }

    public List<Uri> getImageUris() {
        return imageUris;
    }
}
