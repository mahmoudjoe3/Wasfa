package com.mahmoudjoe3.wasfa.repo;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.networking.FoodApi;
import com.mahmoudjoe3.wasfa.pojo.UserPost;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
