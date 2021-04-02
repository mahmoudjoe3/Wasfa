package com.mahmoudjoe3.wasfa.ui.main.fav;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.ui.activities.profile.profileActivity;


public class FavoritesFragment extends Fragment {
    FavoritesViewModel viewModel;
    public FavoritesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this).get(FavoritesViewModel.class);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorites, container, false);
        TextView textView=view.findViewById(R.id.textvi);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), profileActivity.class));
            }
        });
        return view;
    }
}