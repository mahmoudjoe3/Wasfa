package com.mahmoudjoe3.wasfa.ui.main.fav.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mahmoudjoe3.wasfa.ui.main.fav.Interaction;

@Database(entities = Interaction.class, version = 1, exportSchema = false)
public abstract class InteractionsDatabase extends RoomDatabase {
    private static InteractionsDatabase instance;
    public abstract InteractionsDao interactionsDao();

    public static synchronized InteractionsDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, InteractionsDatabase.class, "interactions_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
