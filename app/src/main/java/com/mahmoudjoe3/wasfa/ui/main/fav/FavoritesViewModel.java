package com.mahmoudjoe3.wasfa.ui.main.fav;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private MutableLiveData<List<Interaction>> interactionsMutableLiveData = new MutableLiveData<>();

    public void setInteractions() {
        List<Interaction> interactionList = new ArrayList<>();
        interactionList.add(new Interaction("Mahmoud Youssef", "", "Loved", "May 1, 2021 - 3:56 AM"));
        interactionList.add(new Interaction("Mohamed Ayman", "", "Loved", "May 1, 2021 - 3:56 AM"));
        interactionList.add(new Interaction("Mohamed Shafik", "", "Shared", "May 1, 2021 - 3:56 AM"));
        interactionList.add(new Interaction("Morsi Mohsen", "", "Shared", "May 1, 2021 - 3:56 AM"));
        interactionList.add(new Interaction("Afify", "", "Follow", "May 1, 2021 - 3:56 AM"));
        interactionList.add(new Interaction("Bakr", "", "Follow", "May 1, 2021 - 3:56 AM"));
        interactionList.add(new Interaction("Mostafa Adel", "", "Loved", "May 1, 2021 - 3:56 AM"));
        interactionsMutableLiveData.setValue(interactionList);
    }

    public MutableLiveData<List<Interaction>> getInteractionsMutableLiveData() {
        return interactionsMutableLiveData;
    }
}
