package com.mahmoudjoe3.wasfa.networking;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.UserPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//https://foodapiv120210521031846.azurewebsites.net/
public interface FoodApi {

    @POST("api/Users")
    Call<JsonObject> Register(@Body UserPost userPost);

    @GET("api/UsersBy")
    Call<JsonObject> login(@Query("userIdentity")String identity);

}
