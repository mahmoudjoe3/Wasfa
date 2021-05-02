package com.mahmoudjoe3.wasfa.ui.main.interaction.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mahmoudjoe3.wasfa.pojo.Interaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface InteractionsDao {

    @Insert
    Completable insertInteraction(Interaction interaction);

    @Delete
    Completable deleteInteraction(Interaction interaction);

    @Query("select * from interactions_table")
    LiveData<List<Interaction>> getInteractions();
}
