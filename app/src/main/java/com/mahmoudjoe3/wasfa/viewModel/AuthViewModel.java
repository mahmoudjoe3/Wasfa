package com.mahmoudjoe3.wasfa.viewModel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.Following;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    FoodRepository foodRepository;

    @ViewModelInject
    public AuthViewModel(final FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Call<JsonObject> login(String identity) {
        return foodRepository.login(identity);
    }


    public Call<JsonObject> Register(UserPost user) {
        return foodRepository.register(user);
    }

}
