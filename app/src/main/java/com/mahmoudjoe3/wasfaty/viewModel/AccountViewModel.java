package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

import retrofit2.Call;

public class AccountViewModel extends ViewModel {
    private FoodRepository repository;

    @ViewModelInject
    public AccountViewModel(FoodRepository repository) {
        this.repository = repository;
    }

    public Call<JsonObject> getUserRecipes(int userId){
        return repository.getUserRecipes(userId);
    }

    public Call<JsonObject> UpdateUser(UserPost user){
        return repository.update(user,user.getId());
    }
    public Call<JsonObject> getUserBy(int id){
        return repository.getUserBy(id);
    }

    public Call<JsonObject> deleteRecipe(int recipeId) {
        return repository.deleteRecipe(recipeId);
    }

}
