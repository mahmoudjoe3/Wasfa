package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;
import com.mahmoudjoe3.wasfaty.repo.NLB_DB_Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HomeViewModel extends ViewModel {

    private FoodRepository repository;
    @ViewModelInject
    public HomeViewModel(FoodRepository repository) {
        this.repository=repository;
    }

    public Call<JsonObject> getAllRecipes() {
        return repository.getAllRecipes();
    }
    public Call<JsonObject> getMostCommonRecipes() {
        return repository.getMostCommonRecipes();
    }
    public Call<JsonObject> follow(Following.followingPost followingPost) {
        return repository.follow(followingPost);
    }
}
