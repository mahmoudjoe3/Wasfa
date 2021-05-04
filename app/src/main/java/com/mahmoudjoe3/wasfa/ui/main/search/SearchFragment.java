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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment {
    SearchViewModel viewModel;
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
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        init();
        initPeopleRecycler();
        initRecipeRecycler();

        MyLogic.setOninteractionClickListener(new MyLogic.OninteractionClickListener() {
            @Override
            public void onshare(Recipe recipe) {
                viewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Shared"));

            }

            @Override
            public void onlove(Recipe recipe) {
                viewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Loved"));

            }

            @Override
            public void onDislove(Recipe recipe) {
                viewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "DisLoved"));

            }

            @Override
            public void onfollow(Recipe recipe) {
                viewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Follow"));

            }
        });
        MyLogic.setOnProfileClickListener(new MyLogic.OnProfileClickListener() {
            @Override
            public void onClick(int userid) {
                viewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        for (User user : users){
                            if(userid==user.getId()){
                                Intent intent=new Intent(getActivity(), profileActivity.class );
                                intent.putExtra(profileActivity.USER_INTENT,user);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                });
            }

            @Override
            public void onAddComment(Comment comment) {
                viewModel.insertInteraction(new Interaction(comment.getUsername(),comment.getUserImageUrl(),"Commented On"));
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
                viewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        for (User user1 : users){
                            if(user.getId()==user1.getId()){
                                Intent intent=new Intent(getActivity(), profileActivity.class );
                                intent.putExtra(profileActivity.USER_INTENT,user);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                });
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
                            peopleSearchRecyclerAdapter.setUserList(userList);
                        }
                    },1000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lotti_search.setVisibility(View.GONE);
                            lotti_search.pauseAnimation();
                            recipeSearchRecyclerAdapter.setRecipeList(recipeList);
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
        userList = new ArrayList<>();
        userList.add(new User(1, "Mahmoud Mamdouh","123", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Mahmoud Mamdouh","123", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Mahmoud Mamdouh","123", "", "bio", ""));
        userList.add(new User(1, "Mahmoud Mamdouh","123", "", "bio", ""));
        userList.add(new User(1, "Youssef Shafik","123", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Youssef Shafik","123", "", "bio", ""));
        userList.add(new User(1, "Youssef Shafik","123", "", "bio", ""));
        userList.add(new User(1, "Youssef Shafik","123", "", getString(R.string.bio_test), ""));
        userList.add(new User(1, "Youssef Shafik","123", "", "bio", ""));
        peopleSearchRecyclerAdapter = new PeopleSearchRecyclerAdapter();
        peopleRecyclerView.setAdapter(peopleSearchRecyclerAdapter);
    }

    private void initRecipeRecycler() {
        recipesRecyclerView = view.findViewById(R.id.recipes_recyclerView);
        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1, 2, "Mahmoud Mamdouh Abdullah", "",
                "", getString(R.string.tst),"eg", 1000000, "24min",
                new ArrayList<String>(Arrays.asList("Healthy")), new ArrayList<String>(),
                new ArrayList<String>(), 120, 50, new ArrayList<String>(Arrays.asList("a", "b"))));
        recipeSearchRecyclerAdapter = new RecipeSearchRecyclerAdapter();
        recipesRecyclerView.setAdapter(recipeSearchRecyclerAdapter);
    }
}