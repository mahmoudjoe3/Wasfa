package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

import retrofit2.Call;

public class ProfileViewModel extends ViewModel {
    private FoodRepository repository;

    @ViewModelInject
    public ProfileViewModel(FoodRepository repository) {
        this.repository = repository;
    }

    public Call<JsonObject> getUserRecipes(int userId){
       return repository.getUserRecipes(userId);
    }

    public Call<JsonObject> getUserBy(int id) {
        return repository.getUserBy(id);
    }
    public Call<JsonObject> follow(Following.followingPost followingPost) {
        return repository.follow(followingPost);
    }

    public Call<JsonObject> unFollow(int userId){
        return repository.unFollow(userId);
    }

}
