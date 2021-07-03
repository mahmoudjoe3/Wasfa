package com.mahmoudjoe3.wasfaty.di;

import com.mahmoudjoe3.wasfaty.networking.FoodApi;

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
    public FoodApi getFoodApi(){      //old https://foodapiv120210521031846.azurewebsites.net/
        return new Retrofit.Builder() //new https://foodapiv120210702153043.azurewebsites.net/
                .baseUrl("https://foodapiv120210702153043.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi.class);
    }
}
