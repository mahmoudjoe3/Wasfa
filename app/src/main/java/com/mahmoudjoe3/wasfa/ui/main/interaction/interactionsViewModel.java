package com.mahmoudjoe3.wasfa.ui.main.interaction;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.repo.InteractionRepo;

import java.util.List;

public class interactionsViewModel extends AndroidViewModel {

    private InteractionRepo interactionRepo;
    private LiveData<List<Interaction>> interactionsLiveData;

    public interactionsViewModel(Application application) {
        super(application);
        interactionRepo = InteractionRepo.getInstance(application);
        interactionsLiveData = interactionRepo.getInteractionsLiveData();
    }



    public void deleteInteraction(Interaction interaction) {
        interactionRepo.deleteInteraction(interaction);
    }

    public LiveData<List<Interaction>> getInteractionsLiveData() {
        return interactionsLiveData;
    }
}
