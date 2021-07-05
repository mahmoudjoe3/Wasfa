package com.mahmoudjoe3.wasfaty.networking;

import androidx.room.Delete;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.CommentPost;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

//https://foodapiv120210702153043.azurewebsites.net/api/CommenRecipe/10
public interface FoodApi {
    @POST("api/Users")
    Call<JsonObject> Register(@Body UserPost userPost);
    @POST("/api/Recipes")
    Call<JsonObject> PostRecipe(@Body RecipePost recipe);
    @GET("/api/Recipes")
    Call<JsonObject> getAllRecipes();
    @GET("/api/SomeRecipe/{number}")
    Call<JsonObject> getRangedRecipes(@Path("number") int number);


    @GET("api/UsersByIdentity/{userIdentity}")
    Call<JsonObject> login(@Path("userIdentity") String identity);

    @GET("/api/RecipeByUserId/{userId}")
    Call<JsonObject> getUserRecipes(@Path("userId") int userId);

    @GET("/api/UsersById/{userId}")
    Call<JsonObject> getUserBy(@Path("userId") int id);

    @POST("/api/UsersPostFollowing")
    Call<JsonObject> follow(@Body Following.followingPost followingPost);

    @DELETE("/api/UsersDeleteFollowing/{id}")
    Call<JsonObject> unFollow(@Path("id") int userID);

    @PUT("/api/Users/{id}")
    Call<JsonObject> updateUser(@Body UserPost user,@Path("id") int id);

    @GET("/api/UsersSearch/{Identity}")
    Call<JsonObject> searchUsers(@Path("Identity") String identity);

    @GET("/api/RecipesSearch/{Identity}")
    Call<JsonObject> searchRecipes(@Path("Identity") String identity);

    @POST("/api/PostLove/{RecipeId}")
    Call<JsonObject> love(@Path("RecipeId") int recipeID);

    @POST("/api/PostDisLove/{RecipeId}")
    Call<JsonObject> disLove(@Path("RecipeId") int recipeID);

    @POST("/api/PostShare/{RecipeId}")
    Call<JsonObject> share(@Path("RecipeId") int recipeID);

    @GET("/api/CommenRecipe/{number}")
    Call<JsonObject> getCommonRecipes(@Path("number") int limit);

    @POST("/api/Reviews")
    Call<String> postComment(@Body CommentPost comment);

    @DELETE("/api/Recipes/{id}")
    Call<JsonObject> deleteRecipe(@Path("id") int id);

}
