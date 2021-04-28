package com.mahmoudjoe3.wasfa.ui.main.search;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.ui.main.fav.FavoritesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class SearchFragment extends Fragment {
    SearchViewModel viewModel;

    private EditText searchEditText;
    private TextView peopleTextView, recipesTextView;
    private RecyclerView peopleRecyclerView, recipesRecyclerView;
    private View peopleView, recipesView;
    private ImageButton searchImageButton, backImageButton;
    private PeopleSearchRecyclerAdapter peopleSearchRecyclerAdapter;
    private List<User> userList;
    private RecipeSearchRecyclerAdapter recipeSearchRecyclerAdapter;
    private List<Recipe> recipeList;
    private View view;

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
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        init();
        initPeopleRecycler();
        initRecipeRecycler();

        peopleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleView.setVisibility(View.VISIBLE);
                recipesView.setVisibility(View.INVISIBLE);
                peopleRecyclerView.setVisibility(View.VISIBLE);
                recipesRecyclerView.setVisibility(View.GONE);
                searchImageButton.setTag("people");
            }
        });

        recipesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleView.setVisibility(View.INVISIBLE);
                recipesView.setVisibility(View.VISIBLE);
                peopleRecyclerView.setVisibility(View.GONE);
                recipesRecyclerView.setVisibility(View.VISIBLE);
                searchImageButton.setTag("recipes");
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchImageButton.getTag().toString().equals("people")) {
                    List<User> list = new ArrayList<>();
                    for(int i = 0; i < userList.size(); i ++) {
                        if(userList.get(i).getName().contains(searchEditText.getText().toString())) {
                            list.add(userList.get(i));
                        }
                    }
                    peopleSearchRecyclerAdapter.setUserList(list);
                } else {
                    recipeSearchRecyclerAdapter.setRecipeList(recipeList);
                }
            }
        });

        return view;
    }

    private void init() {
        searchEditText = view.findViewById(R.id.search_editText);
        peopleTextView = view.findViewById(R.id.people_textView);
        recipesTextView = view.findViewById(R.id.recipes_textView);
        peopleView = view.findViewById(R.id.peopleView);
        recipesView = view.findViewById(R.id.recipesView);
        searchImageButton = view.findViewById(R.id.search_imageButton);
        backImageButton = view.findViewById(R.id.back_imageButton);
    }

    private void  initPeopleRecycler() {
        peopleRecyclerView = view.findViewById(R.id.people_recyclerView);
        userList = new ArrayList<>();
        userList.add(new User(1, "Mahmoud Mamdouh", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Mahmoud Mamdouh", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Mahmoud Mamdouh", "", "bio", ""));
        userList.add(new User(1, "Mahmoud Mamdouh", "", "bio", ""));
        userList.add(new User(1, "Youssef Shafik", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Youssef Shafik", "", "bio", ""));
        userList.add(new User(1, "Youssef Shafik", "", "bio", ""));
        userList.add(new User(1, "Youssef Shafik", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Youssef Shafik", "", "bio", ""));
        peopleSearchRecyclerAdapter = new PeopleSearchRecyclerAdapter();
        peopleRecyclerView.setAdapter(peopleSearchRecyclerAdapter);
    }

    private void initRecipeRecycler () {
        recipesRecyclerView = view.findViewById(R.id.recipes_recyclerView);
        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1,2 , "Mahmoud Mamdouh Abdullah", "",
                "", getString(R.string.tst), 1000000, "24min",
                new ArrayList<String>(Arrays.asList("Healthy")), new ArrayList<String>(),
                new ArrayList<String>(), 120, 50, new ArrayList<String>(Arrays.asList("a","b"))));
        recipeSearchRecyclerAdapter = new RecipeSearchRecyclerAdapter();
        recipesRecyclerView.setAdapter(recipeSearchRecyclerAdapter);
    }
}