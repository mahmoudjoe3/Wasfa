package com.mahmoudjoe3.wasfa.pojo;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {
    private int id;
    private int userId;
    private String userName;
    private String userProfileThumbnail;
    private String title;
    private String description;
    private String nationality;
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
                  String description, String nationality, long postTime, String prepareTime, List<String> categories,
                  List<String> ingredients, List<String> steps, int numberOfLike,
                  int numberOfShare, List<String> imgUrls) {
        this.id=id;
        this.userId = userId;
        this.userName = userName;
        this.userProfileThumbnail = userProfileThumbnail;
        this.title = title;
        this.description = description;
        this.nationality = nationality;
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

    public static List<Recipe> parseJson(String jsonResponse) {
        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray recipeArray = jsonObject.getJSONArray("Recipes");

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
        return new ArrayList<String>(Arrays.asList(string.split(",")));
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
