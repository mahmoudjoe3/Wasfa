package com.mahmoudjoe3.wasfaty.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.database.InteractionsDao;
import com.mahmoudjoe3.wasfaty.networking.FoodApi;
import com.mahmoudjoe3.wasfaty.pojo.CommentPost;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Interaction;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.prevalent.prevalent;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier;

import java.io.IOException;
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

    public Call<JsonObject> deleteRecipe(int recipeId) {
        return foodApi.deleteRecipe(recipeId);
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

    public Call<String> postComment(CommentPost comment) {
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

    public double BertClassify(String commentText, Context context) {
            // Initialization
            BertNLClassifier classifier2 = null;
            try {
                classifier2 = BertNLClassifier.createFromFile(context, prevalent.modelFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BertNLClassifier finalClassifier1 = classifier2;
            List<Category> results = finalClassifier1.classify(commentText);
            Log.d("tag###", "onClick: " + commentText + results.get(0).getLabel() + results.get(0).getScore()
                    + results.get(1).getLabel() + results.get(1).getScore());
            double neg = results.get(0).getScore();
            double pos = results.get(1).getScore();
            return (neg > pos) ? -1 * neg : pos;
    }
}
