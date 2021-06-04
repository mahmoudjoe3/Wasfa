package com.mahmoudjoe3.wasfaty.pojo;

import java.util.List;

public class RecipePost {

    private int Id;
    private String Title;
    private String Description;
    private String PrepareTime;
    private String Nationality;
    private String Ingredients;
    private List<String> Categories;
    private String Steps;
    private String Privacy;
    private int LoveCount;
    private int ShareCount;
    private int ReviewsCount;
    private int ReviewsRateTotal;
    private long CreatedDate;
    private List<String> ImagesUrls;
    private int UserId;

    public RecipePost(int id, String title, String description, String prepareTime, String nationality,
                      String ingredients, List<String> categories, String steps, String privacy, int loveCount,
                      int shareCount, int reviewsCount, int reviewsRateTotal, long createdDate, List<String> imagesUrls,
                      int userId) {
        Id = id;
        Title = title;
        Description = description;
        PrepareTime = prepareTime;
        Nationality = nationality;
        Ingredients = ingredients;
        Categories = categories;
        Steps = steps;
        Privacy = privacy;
        LoveCount = loveCount;
        ShareCount = shareCount;
        ReviewsCount = reviewsCount;
        ReviewsRateTotal = reviewsRateTotal;
        CreatedDate = createdDate;
        ImagesUrls = imagesUrls;
        UserId = userId;
    }

    public static RecipePost makeRecipe(Recipe recipe) {
        return new RecipePost(recipe.getId(),recipe.getTitle(),recipe.getDescription(),recipe.getPrepareTime()
                ,recipe.getNationality(),listtoString(recipe.getIngredients()),recipe.getCategories(),
                listtoString(recipe.getSteps()), recipe.getPrivacy(),recipe.getLoveCount(),recipe.getShareCount(),
                0,0, recipe.getCreatedDate(),recipe.getImgUrls(),recipe.getUserId());
    }

    private static String listtoString(List<String> strArr) {
        StringBuilder stringBuilder=new StringBuilder();
        for (String s:strArr) {
            stringBuilder.append(s);
            stringBuilder.append("~");
        }
        return stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
    }


    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPrepareTime() {
        return PrepareTime;
    }

    public void setPrepareTime(String PrepareTime) {
        this.PrepareTime = PrepareTime;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String Ingredients) {
        this.Ingredients = Ingredients;
    }

    public List<String> getCategories() {
        return Categories;
    }

    public void setCategories(List<String> Categories) {
        this.Categories = Categories;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String Steps) {
        this.Steps = Steps;
    }

    public String getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(String Privacy) {
        this.Privacy = Privacy;
    }

    public int getLoveCount() {
        return LoveCount;
    }

    public void setLoveCount(int LoveCount) {
        this.LoveCount = LoveCount;
    }

    public int getShareCount() {
        return ShareCount;
    }

    public void setShareCount(int ShareCount) {
        this.ShareCount = ShareCount;
    }

    public int getReviewsCount() {
        return ReviewsCount;
    }

    public void setReviewsCount(int ReviewsCount) {
        this.ReviewsCount = ReviewsCount;
    }

    public int getReviewsRateTotal() {
        return ReviewsRateTotal;
    }

    public void setReviewsRateTotal(int ReviewsRateTotal) {
        this.ReviewsRateTotal = ReviewsRateTotal;
    }

    public long getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(int CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public void setCreatedDate(long createdDate) {
        CreatedDate = createdDate;
    }

    public List<String> getImagesUrls() {
        return ImagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        ImagesUrls = imagesUrls;
    }
}
