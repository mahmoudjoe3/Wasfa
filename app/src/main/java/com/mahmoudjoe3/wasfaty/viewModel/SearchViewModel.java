package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.User;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SearchViewModel extends ViewModel {

    private FoodRepository repository;

    @ViewModelInject
    public SearchViewModel(FoodRepository repository) {
        this.repository=repository;
    }

    public Call<JsonObject> searchUsers(String identity) {
        return repository.searchUsers(identity);
    }

    public Call<JsonObject> searchRecipes(String identity) {
        return repository.searchRecipes(identity);
    }

    public Call<JsonObject> follow(Following.followingPost followingPost) {
        return repository.follow(followingPost);
    }
}
