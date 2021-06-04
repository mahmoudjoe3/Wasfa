package com.mahmoudjoe3.wasfaty.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mahmoudjoe3.wasfaty.pojo.Interaction;

@Database(entities = Interaction.class, version = 1, exportSchema = false)
public abstract class InteractionsDatabase extends RoomDatabase {
    public abstract InteractionsDao getinteractionsDao();
}
