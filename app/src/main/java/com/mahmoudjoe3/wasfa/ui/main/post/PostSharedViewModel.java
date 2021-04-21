package com.mahmoudjoe3.wasfa.ui.main.post;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.pojo.Recipe;

public class PostSharedViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeMutableLiveData = new MutableLiveData<>();


    public void setRecipe (Recipe recipe) {
        recipeMutableLiveData.setValue(recipe);
    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return recipeMutableLiveData;
    }
}
