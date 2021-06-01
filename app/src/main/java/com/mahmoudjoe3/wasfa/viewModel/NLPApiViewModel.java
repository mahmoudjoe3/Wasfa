package com.mahmoudjoe3.wasfa.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.repo.NLB_DB_Repository;


public class NLPApiViewModel extends ViewModel {
    private MutableLiveData<String > responseMutableLiveData=new MutableLiveData<>();
    private NLB_DB_Repository repository;

    @ViewModelInject
    public NLPApiViewModel(NLB_DB_Repository repository) {
        this.repository = repository;
    }
    public LiveData<String > getSentimentAnalysis(){
        return responseMutableLiveData;
    }
    public void classifyText(String text){
        repository.analyzeTextUsingCloudNLPApi(text);
        repository.setOnNLPAPIResponse(new NLB_DB_Repository.OnNLPAPIResponse() {
            @Override
            public void onResponse(String response) {
                responseMutableLiveData.postValue(response);
            }
        });
    }
}
