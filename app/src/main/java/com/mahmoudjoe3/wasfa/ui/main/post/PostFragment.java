package com.mahmoudjoe3.wasfa.ui.main.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.viewModel.PostViewModel;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostFragment extends Fragment   {
    PostViewModel viewModel;
    public PostFragment() {
        // Required empty public constructor
    }
    private static PostFragment postFragment;

    public static PostFragment getInstance() {
        if (postFragment == null) {
            postFragment = new PostFragment();
        }
        return postFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    PostFragment1.getInstance()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(PostViewModel.class);

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_post, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




}