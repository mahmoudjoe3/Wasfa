package com.mahmoudjoe3.wasfa.ui.main.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.ui.activities.profile.profileActivity;
import com.mahmoudjoe3.wasfa.viewModel.InteractionsViewModel;
import com.mahmoudjoe3.wasfa.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment {
    SearchViewModel searchViewModel;
    InteractionsViewModel interactionsViewModel;
    @BindView(R.id.default_search_layout)
    LinearLayout defaultSearchLayout;
    @BindView(R.id.no_result_search_layout)
    LinearLayout noResultSearchLayout;
    @BindView(R.id.lotti_no_result)
    LottieAnimationView lottiNoResult;

    private EditText searchEditText;
    private TextView peopleTextView, recipesTextView;
    private RecyclerView peopleRecyclerView, recipesRecyclerView;
    private View peopleView, recipesView;
    private ImageButton searchImageButton, backImageButton;
    private LottieAnimationView lotti_search;
    private PeopleSearchRecyclerAdapter peopleSearchRecyclerAdapter;
    private List<User> userList;
    User user=new User();
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
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        interactionsViewModel=new ViewModelProvider(this).get(InteractionsViewModel.class);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        init();
        initPeopleRecycler();
        initRecipeRecycler();

        MyLogic.setOninteractionClickListener(new MyLogic.OninteractionClickListener() {
            @Override
            public void onshare(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Shared"));

            }

            @Override
            public void onlove(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Loved"));

            }

            @Override
            public void onDislove(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "DisLoved"));

            }

            @Override
            public void onfollow(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Follow"));

            }
        });
        MyLogic.setOnProfileClickListener(new MyLogic.OnProfileClickListener() {

            @Override
            public void onAddComment(Comment comment) {
                interactionsViewModel.insertInteraction(new Interaction(comment.getUsername(),comment.getUserImageUrl(),"Commented On"));
            }
        });


        recipeSearchRecyclerAdapter.setOnItemClickListener(new RecipeSearchRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(getActivity(),recipe,user);
            }
        });

        peopleSearchRecyclerAdapter.setOnItemClickListener(new PeopleSearchRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(User user) {
                //todo open profile
                Intent intent=new Intent(getActivity(), profileActivity.class );
                intent.putExtra(profileActivity.USER_INTENT,user.getId());
                startActivity(intent);
            }

            @Override
            public void onFollow(User user) {
                interactionsViewModel.insertInteraction(new Interaction(user.getName(),user.getImageUrl(),"Followed"));
            }
        });

        peopleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleRecyclerView.setVisibility(View.VISIBLE);
                recipesRecyclerView.setVisibility(View.GONE);
                peopleView.setVisibility(View.VISIBLE);
                recipesView.setVisibility(View.INVISIBLE);
                searchImageButton.setTag("people");
                searchEditText.setHint(R.string.search_people);
            }
        });

        recipesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleRecyclerView.setVisibility(View.GONE);
                recipesRecyclerView.setVisibility(View.VISIBLE);
                peopleView.setVisibility(View.INVISIBLE);
                recipesView.setVisibility(View.VISIBLE);
                searchImageButton.setTag("recipes");
                searchEditText.setHint(R.string.search_recipe);
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
                lotti_search.setVisibility(View.VISIBLE);
                lotti_search.playAnimation();
                //todo after recive data puase lotti searh and hide
                //todo if data is empty noResultSearchLayout.setVisibility(View.VISIBLE);
                defaultSearchLayout.setVisibility(View.GONE);
                if (searchImageButton.getTag().toString().equals("people")) {
                    peopleRecyclerView.setVisibility(View.VISIBLE);
                    recipesRecyclerView.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lotti_search.setVisibility(View.GONE);
                            lotti_search.pauseAnimation();
                            List<User> temp=new ArrayList<>();
                            for(User u:userList){
                                if(u.getName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())){
                                    temp.add(u);
                                }
                            }
                            peopleSearchRecyclerAdapter.setUserList(temp);
                        }
                    },1000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lotti_search.setVisibility(View.GONE);
                            lotti_search.pauseAnimation();
                            List<Recipe> temp=new ArrayList<>();
                            for(Recipe r:recipeList){
                                if(r.getCategories().toString().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())
                                    ||r.getIngredients().toString().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())
                                        ||r.getDescription().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())
                                        ||r.getNationality().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())
                                        ||r.getUserName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())
                                ){
                                    temp.add(r);
                                }
                            }
                            recipeSearchRecyclerAdapter.setRecipeList(temp);
                        }
                    },1000);
                    peopleRecyclerView.setVisibility(View.GONE);
                    recipesRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void init() {
        lotti_search = view.findViewById(R.id.lotti_search);
        searchEditText = view.findViewById(R.id.search_editText);
        peopleTextView = view.findViewById(R.id.people_textView);
        recipesTextView = view.findViewById(R.id.recipes_textView);
        peopleView = view.findViewById(R.id.peopleView);
        recipesView = view.findViewById(R.id.recipesView);
        searchImageButton = view.findViewById(R.id.search_imageButton);
        backImageButton = view.findViewById(R.id.back_imageButton);
    }

    private void initPeopleRecycler() {
        peopleRecyclerView = view.findViewById(R.id.people_recyclerView);
        searchViewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList=users;
            }
        });
        peopleSearchRecyclerAdapter = new PeopleSearchRecyclerAdapter();
        peopleRecyclerView.setAdapter(peopleSearchRecyclerAdapter);
    }

    private void initRecipeRecycler() {
        recipesRecyclerView = view.findViewById(R.id.recipes_recyclerView);
        recipeSearchRecyclerAdapter = new RecipeSearchRecyclerAdapter();
        searchViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeList=recipes;
            }
        });
        recipesRecyclerView.setAdapter(recipeSearchRecyclerAdapter);
    }
}