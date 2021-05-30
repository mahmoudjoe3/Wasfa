package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.repo.NLB_DB_Repository;

import java.util.List;

public class InteractionsViewModel extends ViewModel {

    private NLB_DB_Repository repository;
    private LiveData<List<Interaction>> interactionsLiveData;

    @ViewModelInject
    public InteractionsViewModel(NLB_DB_Repository repository) {
        this.repository = repository;
    }


    public void insertInteraction(Interaction interaction) {
        repository.insertInteraction(interaction);
    }
    public void deleteInteraction(Interaction interaction) {
        repository.deleteInteraction(interaction);
    }

    public void fitchInteractions(){interactionsLiveData=repository.getInteractionsLiveData();}
    public LiveData<List<Interaction>> getInteractionsLiveData() {
        return interactionsLiveData;
    }


}
