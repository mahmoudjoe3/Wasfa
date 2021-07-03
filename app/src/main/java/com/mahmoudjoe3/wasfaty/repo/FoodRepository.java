package com.mahmoudjoe3.wasfaty.repo;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.database.InteractionsDao;
import com.mahmoudjoe3.wasfaty.networking.FoodApi;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Interaction;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class FoodRepository {
    private FoodApi foodApi;
    //DB
    private InteractionsDao dao;

    @Inject
    public FoodRepository(final FoodApi foodApi, InteractionsDao dao) {
        this.foodApi = foodApi;
        this.dao = dao;
    }

    public Call<JsonObject> login(String identity) {
        return foodApi.login(identity);
    }

    public Call<JsonObject> register(UserPost userPost) {
        return foodApi.Register(userPost);
    }

    public Call<JsonObject> getUserRecipes(int userId) {
        return foodApi.getUserRecipes(userId);
    }

    public Call<JsonObject> getUserBy(int id) {
        return foodApi.getUserBy(id);
    }

    public Call<JsonObject> follow(Following.followingPost followingPost) {
        return foodApi.follow(followingPost);
    }

    public Call<JsonObject> update(UserPost user, int id) {
        return foodApi.updateUser(user, id);
    }

    public Call<JsonObject> searchUsers(String identity) {
        return foodApi.searchUsers(identity);
    }

    public Call<JsonObject> searchRecipes(String identity) {
        return foodApi.searchRecipes(identity);
    }

    public Call<JsonObject> postRecipe(RecipePost recipe) {
        return foodApi.PostRecipe(recipe);
    }

    public Call<JsonObject> getAllRecipes() {
        return foodApi.getAllRecipes();
    }
    public Call<JsonObject> getMostCommonRecipes(int limit) {
        return foodApi.getCommonRecipes(limit);
    }

    public Call<JsonObject> love(int recipeId){
        return foodApi.love(recipeId);
    }

    public Call<JsonObject> dislove(int recipeID) {
        return foodApi.disLove(recipeID);
    }

    public Call<JsonObject> share(int recipeID) {
        return foodApi.share(recipeID);
    }

    public Call<JsonObject> unFollow(int userID){
        return foodApi.unFollow(userID);
    }

    public Call<String> postComment(Comment comment) {
        return foodApi.postComment(comment);
    }
    //DB
    public void insertInteraction(Interaction interaction) {
        dao.insertInteraction(interaction)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteInteraction(Interaction interaction) {
        dao.deleteInteraction(interaction)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public LiveData<List<Interaction>> getInteractionsLiveData() {
        return dao.getInteractions();
    }


    public Call<JsonObject> getRangedRecipes(int page, int limit) {
        return foodApi.getRangedRecipes(page);
    }
}
