package com.mahmoudjoe3.wasfa.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    MutableLiveData<String> data=new MutableLiveData<>();

    public LiveData<String> getData() {
        return data;
    }

    public void setData(String data) {
        this.data.postValue(data);
    }
}
