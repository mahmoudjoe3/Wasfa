package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

import retrofit2.Call;

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
