package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;

import retrofit2.Call;

public class PostViewModel extends ViewModel {
    private FoodRepository repository;

    @ViewModelInject
    public PostViewModel(FoodRepository repository) {
        this.repository = repository;
    }

    public Call<JsonObject> getUserRecipes(int userId){
        return repository.getUserRecipes(userId);
    }

}
