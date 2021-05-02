package com.mahmoudjoe3.wasfa.ui.main.fav.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mahmoudjoe3.wasfa.ui.main.fav.Interaction;

import java.util.List;
import java.util.Observable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface InteractionsDao {

    @Insert
    Completable insertInteraction(Interaction interaction);

    @Delete
    Completable deleteInteraction(Interaction interaction);

    @Query("select * from interactions_table")
    LiveData<List<Interaction>> getInteractions();
}
