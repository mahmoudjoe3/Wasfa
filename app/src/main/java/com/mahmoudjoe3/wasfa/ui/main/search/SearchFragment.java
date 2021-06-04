package com.mahmoudjoe3.wasfa.ui.main.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.ui.activities.profile.profileActivity;
import com.mahmoudjoe3.wasfa.viewModel.InteractionsViewModel;
import com.mahmoudjoe3.wasfa.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

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
    UserPost user;
    private RecipeSearchRecyclerAdapter recipeSearchRecyclerAdapter;
    private View view;

    private static final String SHARED_PREFERENCE_NAME = "userShared";


    public SearchFragment() {
        // Required empty public constructor
    }

    private static SearchFragment searchFragment;

    public static SearchFragment getInstance() {
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
        }
        return searchFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        interactionsViewModel = new ViewModelProvider(this).get(InteractionsViewModel.class);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        /*
         ** SharedPreference Code to get the logged in user
         */
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        user = gson.fromJson(sharedPreferences.getString("user", null), UserPost.class);

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
            public void onClick(UserPost user) {
                //todo open profile
                Intent intent=new Intent(getActivity(), profileActivity.class );
                intent.putExtra(profileActivity.USER_INTENT,user.getId());
                startActivity(intent);
            }

            @Override
            public void onFollow(UserPost user) {
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
                peopleSearchRecyclerAdapter.setUserList(new ArrayList<>());
                recipeSearchRecyclerAdapter.setRecipeList(new ArrayList<>());
                searchEditText.setText("");
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
                    String searchKey = searchEditText.getText().toString().toLowerCase();
                    searchViewModel.searchUsers(searchKey).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.code() >= 200 && response.code() < 300) {
                                lotti_search.setVisibility(View.GONE);
                                lotti_search.pauseAnimation();
                                List<UserPost> userPostList = UserPost.parseUserResponseList(response.body().toString());
                                peopleSearchRecyclerAdapter.setUserList(userPostList);
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });

                } else {
                    peopleRecyclerView.setVisibility(View.GONE);
                    recipesRecyclerView.setVisibility(View.VISIBLE);
                    String searchKey = searchEditText.getText().toString().toLowerCase();
                    searchViewModel.searchRecipes(searchKey).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.code() >= 200 && response.code() < 300) {
                                String jsonString = response.body().toString();
                                List<Recipe> recipes = Recipe.parseRecipeJson(jsonString);
                                recipeSearchRecyclerAdapter.setRecipeList(recipes);
                                lotti_search.setVisibility(View.GONE);
                                lotti_search.pauseAnimation();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
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
        peopleSearchRecyclerAdapter = new PeopleSearchRecyclerAdapter();
        peopleRecyclerView.setAdapter(peopleSearchRecyclerAdapter);
    }

    private void initRecipeRecycler() {
        recipesRecyclerView = view.findViewById(R.id.recipes_recyclerView);
        recipeSearchRecyclerAdapter = new RecipeSearchRecyclerAdapter();
        recipesRecyclerView.setAdapter(recipeSearchRecyclerAdapter);
    }
}