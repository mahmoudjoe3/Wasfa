package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfaty.pojo.Interaction;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;

import java.util.List;

public class InteractionsViewModel extends ViewModel {

    private FoodRepository repository;
    private LiveData<List<Interaction>> interactionsLiveData;

    @ViewModelInject
    public InteractionsViewModel(FoodRepository repository) {
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
