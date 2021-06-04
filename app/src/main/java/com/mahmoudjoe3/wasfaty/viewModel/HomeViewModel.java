package com.mahmoudjoe3.wasfaty.viewModel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.repo.FoodRepository;
import com.mahmoudjoe3.wasfaty.repo.NLB_DB_Repository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private FoodRepository repository;
    @ViewModelInject
    public HomeViewModel(FoodRepository repository) {
        this.repository=repository;
    }


}
