package com.mahmoudjoe3.wasfa.networking;

import com.google.api.client.json.Json;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.Following;
import com.mahmoudjoe3.wasfa.pojo.UserPost;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

//https://foodapiv120210521031846.azurewebsites.net/
public interface FoodApi {

    @POST("api/Users")
    Call<JsonObject> Register(@Body UserPost userPost);

    @GET("api/UsersByIdentity/{userIdentity}")
    Call<JsonObject> login(@Path("userIdentity") String identity);

    @GET("/api/RecipeByUserId/{userId}")
    Call<JsonObject> getUserRecipes(@Path("userId") int userId);

    @GET("/api/UsersById/{userId}")
    Call<JsonObject> getUserBy(@Path("userId") int id);

    @POST("/api/UsersPostFollowing")
    Call<JsonObject> follow(@Body Following.followingPost followingPost);
    @PUT("/api/Users/{id}")
    Call<JsonObject> updateUser(@Body UserPost user,@Path("id") int id);

    @GET("/api/UsersSearch/{Identity}")
    Call<JsonObject> searchUsers(@Path("Identity") String identity);

    @GET("/api/RecipesSearch/{Identity}")
    Call<JsonObject> searchRecipes(@Path("Identity") String identity);
}
