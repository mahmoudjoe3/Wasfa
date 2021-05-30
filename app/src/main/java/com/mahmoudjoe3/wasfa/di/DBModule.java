package com.mahmoudjoe3.wasfa.di;

import android.app.Application;

import androidx.room.Room;

import com.mahmoudjoe3.wasfa.database.InteractionsDao;
import com.mahmoudjoe3.wasfa.database.InteractionsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DBModule {

    @Provides
    @Singleton
    public static InteractionsDatabase getInteractionsDatabase(Application application){
        return Room.databaseBuilder(application,InteractionsDatabase.class,"interactions_data")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static InteractionsDao getInteractionsDao(InteractionsDatabase interactionsDatabase){
        return interactionsDatabase.getinteractionsDao();
    }
}
