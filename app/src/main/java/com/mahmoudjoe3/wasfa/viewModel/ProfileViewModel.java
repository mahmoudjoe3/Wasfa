package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Following;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;
import com.mahmoudjoe3.wasfa.repo.NLB_DB_Repository;

import java.util.ArrayList;
import java.util.List;

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

}
