package com.mahmoudjoe3.wasfaty.ui.main.home;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Interaction;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.prevalent.prevalent;
import com.mahmoudjoe3.wasfaty.ui.activities.profile.profileActivity;
import com.mahmoudjoe3.wasfaty.viewModel.HomeViewModel;
import com.mahmoudjoe3.wasfaty.viewModel.InteractionsViewModel;
import com.mahmoudjoe3.wasfaty.ui.main.account.profilePostItemAdapter;
import com.mahmoudjoe3.wasfaty.ui.main.viewImage.ViewImageActivity;

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
public class HomeFragment extends Fragment {
    private static final String TAG = "homey";
    private static final String SHARED_PREFERENCE_NAME = "userShared";
    HomeViewModel homeViewModel;
    InteractionsViewModel interactionsViewModel;
    UserPost mUser;
    @BindView(R.id.shimmer_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.mostCommonRecycle)
    RecyclerView mostCommonRecycle;
    private int REC_AUTH_CODE = 1;

    RecipePostAdapter adapter;


    profilePostItemAdapter mostCommonAdapter;

    private List<Recipe> recipeList;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        interactionsViewModel = new ViewModelProvider(this).get(InteractionsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        gson = new Gson();
        mUser = gson.fromJson(sharedPreferences.getString("user", null), UserPost.class);


        setupRecipeAdapter();

        setupMostCommonAdapter();

        return view;

    }

    private void setupMostCommonAdapter() {
        mostCommonAdapter =new profilePostItemAdapter(prevalent.COMMON_ITEM);
        mostCommonRecycle.setAdapter(mostCommonAdapter);
        mostCommonRecycle.setHasFixedSize(true);
        homeViewModel.getMostCommonRecipes().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()>=200&&response.code()<300) {
                    mostCommonAdapter.setRecipeList(Recipe.parseRecipeJson(response.body().toString()));
                }else {
                    mostCommonAdapter.setRecipeList(new ArrayList<>());
                    Toast.makeText(getActivity(), "Most common Error Response code "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mostCommonAdapter.setRecipeList(new ArrayList<>());
                Toast.makeText(getActivity(), "Most common Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mostCommonAdapter.setmOnItemClickListener(new profilePostItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(getActivity(),recipe,mUser);
            }
        });
    }

    private void setupRecipeAdapter() {
        adapter = new RecipePostAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        homeViewModel.getAllRecipes().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()>=200&&response.code()<300) {
                    List<Recipe> temp=Recipe.parseRecipeJson(response.body().toString());
                    List<Recipe> temp1=new ArrayList<>();
                    for(Recipe r:temp){
                        if(r.getUserId()==mUser.getId())
                            continue;
                        temp1.add(r);
                    }
                    adapter.setRecipes(temp1);
                    adapter.setFollowing(mUser.getFollowings());
                }else {
                    adapter.setRecipes(new ArrayList<>());
                    Toast.makeText(getActivity(), "Error Response code "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                adapter.setRecipes(new ArrayList<>());
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        adapter.setmOnImageClickListener(new RecipePostAdapter.OnImageClickListener() {
            @Override
            public void onNumberClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(getActivity(), recipe, mUser);
            }

            @Override
            public void onImageClick(List<String> imgUrls, int pos) {
                showImage(imgUrls, pos);
            }
        });

        //comment click listener
        adapter.setmOnCommentClickListener(new RecipePostAdapter.OnCommentClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.openCommentSheet(recipe, getActivity(), mUser);
            }
        });

        adapter.setOninteractionClickListener(new RecipePostAdapter.OninteractionClickListener() {
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
                homeViewModel.follow(new Following.followingPost(0,mUser.getId(),recipe.getUserId())).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code()>=200&&response.code()<300) {
                            interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Follow"));
                        }else
                            Toast.makeText(getActivity(), "response code "+response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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
                homeViewModel.follow(new Following.followingPost(0,mUser.getId(),recipe.getUserId())).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code()>=200&&response.code()<300) {
                            interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Follow"));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        MyLogic.setOnProfileClickListener(new MyLogic.OnProfileClickListener() {

            @Override
            public void onAddComment(Comment comment) {
                interactionsViewModel.insertInteraction(new Interaction(comment.getUsername(),comment.getUserImageUrl(),"Commented On"));

            }
        });

        adapter.setOnProfileClickListener(new RecipePostAdapter.OnProfileClickListener() {
            @Override
            public void onClick(int userid) {
                Intent intent=new Intent(getActivity(),profileActivity.class);
                intent.putExtra(profileActivity.USER_INTENT,userid);
                startActivity(intent);
            }
        });

    }

    private void showImage(List<String> imgUrls, int pos) {
        Intent intent = new Intent(getActivity(), ViewImageActivity.class);
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, new ArrayList<>(imgUrls));
        intent.putExtra(ViewImageActivity.IMG_POS, pos);
        startActivity(intent);
    }
}