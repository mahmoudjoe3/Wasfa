package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.CommentPost;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

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
    public Call<JsonObject> getRangedRecipes(int page,int limit) {
        return repository.getRangedRecipes(page,limit);
    }
    public Call<JsonObject> getMostCommonRecipes(int limit) {
        return repository.getMostCommonRecipes(limit);
    }
    public Call<JsonObject> follow(Following.followingPost followingPost) {
        return repository.follow(followingPost);
    }

    public Call<JsonObject> love(int recipeID) {
        return repository.love(recipeID);
    }

    public Call<JsonObject> disLove(int recipeID) {
        return repository.dislove(recipeID);
    }

    public Call<JsonObject> share(int recipeID) {
        return repository.share(recipeID);
    }

    public Call<String> postComment(CommentPost comment) {
        return repository.postComment(comment);
    }
}
