package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.api.client.json.Json;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;
import com.mahmoudjoe3.wasfa.repo.NLB_DB_Repository;

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
}
