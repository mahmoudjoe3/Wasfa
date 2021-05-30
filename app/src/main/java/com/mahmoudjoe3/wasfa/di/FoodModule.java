package com.mahmoudjoe3.wasfa.di;

import com.mahmoudjoe3.wasfa.networking.FoodApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class FoodModule {

    @Singleton
    @Provides
    public FoodApi getFoodApi(){
        return new Retrofit.Builder()
                .baseUrl("https://foodapiv120210521031846.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi.class);
    }
}
