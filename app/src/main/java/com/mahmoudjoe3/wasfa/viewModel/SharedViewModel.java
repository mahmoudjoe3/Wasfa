package com.mahmoudjoe3.wasfa.viewModel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.repo.FoodRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedViewModel extends ViewModel {
    MutableLiveData<UserPost> user=new MutableLiveData<>();
    FoodRepository repository;

    @ViewModelInject
    public SharedViewModel(FoodRepository repository) {
        this.repository = repository;
    }

    public LiveData<UserPost> getUser(int id) {
        Log.d("TAGkkkk", "getUser: "+id);
        repository.getUserBy(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                user.postValue(UserPost.parseUserRespone(response.body().toString()));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAGkkkk", "####onFailure: "+t.getMessage());
            }
        });
        return user;
    }

}
