package com.mahmoudjoe3.wasfa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mahmoudjoe3.wasfa.pojo.Interaction;

@Database(entities = Interaction.class, version = 1, exportSchema = false)
public abstract class InteractionsDatabase extends RoomDatabase {
    public abstract InteractionsDao getinteractionsDao();
}
