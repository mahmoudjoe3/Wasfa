package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.Following;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;

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


}
