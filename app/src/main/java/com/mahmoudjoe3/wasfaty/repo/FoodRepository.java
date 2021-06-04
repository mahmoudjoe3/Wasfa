package com.mahmoudjoe3.wasfaty.repo;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.networking.FoodApi;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;

import javax.inject.Inject;

import retrofit2.Call;

public class FoodRepository {
    private FoodApi foodApi;

    @Inject
    public FoodRepository(final FoodApi foodApi) {
        this.foodApi = foodApi;
    }

    public Call<JsonObject> login(String identity){
        return foodApi.login(identity);
    }
    public Call<JsonObject> register( UserPost userPost){
        return foodApi.Register(userPost);
    }

    public Call<JsonObject> getUserRecipes( int userId){
        return foodApi.getUserRecipes(userId);
    }
    public Call<JsonObject> getUserBy(int id) {
        return foodApi.getUserBy(id);
    }
    public Call<JsonObject> follow(Following.followingPost followingPost) {
        return foodApi.follow(followingPost);
    }

    public Call<JsonObject> update(UserPost user,int id) {
        return foodApi.updateUser(user,id);
    }

    public Call<JsonObject> searchUsers(String identity) {
        return foodApi.searchUsers(identity);
    }

    public Call<JsonObject> searchRecipes(String identity) {
        return foodApi.searchRecipes(identity);
    }
    public Call<JsonObject> postRecipe(RecipePost recipe) {
        return foodApi.PostRecipe(recipe);
    }

}
