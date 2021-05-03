package com.mahmoudjoe3.wasfa.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.ui.main.interaction.database.InteractionsDao;
import com.mahmoudjoe3.wasfa.ui.main.interaction.database.InteractionsDatabase;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InteractionRepo {
    private InteractionsDao interactionsDao;
    private LiveData<List<Interaction>> interactionsLiveData;

    private static InteractionRepo instance;

    public static synchronized InteractionRepo getInstance(Context context) {
        if(instance == null) {
            instance=new InteractionRepo(context);
        }
        return instance;
    }

    public InteractionRepo(Context context) {
        InteractionsDatabase interactionsDatabase = InteractionsDatabase.getInstance(context);
        interactionsDao = interactionsDatabase.interactionsDao();
        interactionsLiveData = interactionsDao.getInteractions();
    }

    public void insertInteraction(Interaction interaction) {
        interactionsDao.insertInteraction(interaction)
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
        interactionsDao.deleteInteraction(interaction)
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
        return interactionsLiveData;
    }
}
