package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

import retrofit2.Call;

public class PostViewModel extends ViewModel {
    private FoodRepository repository;

    @ViewModelInject
    public PostViewModel(FoodRepository repository) {
        this.repository = repository;
    }

    public Call<JsonObject> PostRecipe(RecipePost recipe){
        return repository.postRecipe(recipe);
    }

}
