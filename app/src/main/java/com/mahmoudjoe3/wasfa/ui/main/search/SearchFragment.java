package com.mahmoudjoe3.wasfa.ui.main.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.ui.main.fav.FavoritesViewModel;


public class SearchFragment extends Fragment {
    SearchViewModel viewModel;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }
}