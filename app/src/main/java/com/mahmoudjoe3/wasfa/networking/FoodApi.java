package com.mahmoudjoe3.wasfa.networking;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.UserPost;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//https://foodapiv120210521031846.azurewebsites.net/
public interface FoodApi {

    @POST("api/Users")
    Call<JsonObject> Register(@Body UserPost userPost);

    @GET("api/UsersBy/{userIdentity}")
    Call<JsonObject> login(@Path("userIdentity") String identity);

}
