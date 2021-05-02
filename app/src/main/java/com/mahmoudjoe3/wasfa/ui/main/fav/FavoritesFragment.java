package com.mahmoudjoe3.wasfa.ui.main.fav;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.ui.activities.profile.profileActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavoritesFragment extends Fragment {
    FavoritesViewModel viewModel;

    private EditText searchEditText;
    private TextView interactionTextView;
    private RecyclerView interactionRecyclerView;
    private ImageButton searchImageButton, backImageButton;
    private InteractionsRecyclerAdapter interactionsRecyclerAdapter;
    private List<Interaction> interactionList;
    View view;

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

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        init();
        initInteractionsRecycler();
        viewModel.getInteractionsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Interaction>>() {
            @Override
            public void onChanged(List<Interaction> interactions) {
                interactionList = interactions;
                interactionsRecyclerAdapter.setInteractionList(interactionList);
            }
        });
        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchImageButton.getTag().toString().equals("not")) {
                    searchEditText.setVisibility(View.VISIBLE);
                    interactionTextView.setVisibility(View.GONE);
                    searchImageButton.setTag("vis");
                    searchImageButton.setImageResource(R.drawable.ic_cancel);
                } else {
                    searchEditText.setVisibility(View.GONE);
                    interactionTextView.setVisibility(View.VISIBLE);
                    searchImageButton.setTag("not");
                    searchImageButton.setImageResource(R.drawable.ic_search_30);
                    searchEditText.setText("");
                }
                viewModel.insertInteraction(new Interaction("Mahmoud Mamdouh","", "Loved", "May 2, 2021 - 5:50Pm"));
            }
        });

        interactionsRecyclerAdapter.setOnItemClickListener(new InteractionsRecyclerAdapter.onItemClickListener() {
            @Override
            public void onDeleteClick(Interaction interaction) {
                viewModel.deleteInteraction(interaction);
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //interactionsRecyclerAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        return view;
    }

    private void initInteractionsRecycler() {
        interactionRecyclerView = view.findViewById(R.id.interactions_recyclerView);
        interactionsRecyclerAdapter = new InteractionsRecyclerAdapter();
        interactionRecyclerView.setAdapter(interactionsRecyclerAdapter);
    }

    private void init() {
        searchImageButton = view.findViewById(R.id.search_imageButton);
        interactionTextView = view.findViewById(R.id.interaction_label);
        searchEditText = view.findViewById(R.id.search_editText);
        backImageButton = view.findViewById(R.id.back_imageButton);
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
    }
}