package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    FoodRepository foodRepository;
    MutableLiveData<User> user=new MutableLiveData<>();
    @ViewModelInject
    public AuthViewModel(final FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    public void login(String identity){
        foodRepository.login(identity).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public LiveData<User> getUser() {
        return user;
    }
    public Call<JsonObject> Register(UserPost user){
        return foodRepository.register(user);
    }

}
