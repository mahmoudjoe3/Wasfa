package com.mahmoudjoe3.wasfa.ui.main.fav.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mahmoudjoe3.wasfa.ui.main.fav.Interaction;
import com.mahmoudjoe3.wasfa.ui.main.fav.database.InteractionsDao;
import com.mahmoudjoe3.wasfa.ui.main.fav.database.InteractionsDatabase;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InteractionRepo {
    private InteractionsDao interactionsDao;
    private LiveData<List<Interaction>> interactionsLiveData;

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
