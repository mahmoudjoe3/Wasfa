package com.mahmoudjoe3.wasfa.ui.main.fav;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mahmoudjoe3.wasfa.ui.main.fav.repo.InteractionRepo;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private InteractionRepo interactionRepo;
    private LiveData<List<Interaction>> interactionsLiveData;

    public FavoritesViewModel(Application application) {
        super(application);
        interactionRepo = new InteractionRepo(application);
        interactionsLiveData = interactionRepo.getInteractionsLiveData();
    }

    public void insertInteraction(Interaction interaction) {
        interactionRepo.insertInteraction(interaction);
    }

    public void deleteInteraction(Interaction interaction) {
        interactionRepo.deleteInteraction(interaction);
    }

    public LiveData<List<Interaction>> getInteractionsLiveData() {
        return interactionsLiveData;
    }
}
