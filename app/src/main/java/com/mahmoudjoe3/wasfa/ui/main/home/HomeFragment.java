package com.mahmoudjoe3.wasfa.ui.main.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.mahmoudjoe3.wasfa.ui.activities.profile.profileActivity;
import com.mahmoudjoe3.wasfa.ui.main.SharedViewModel;
import com.mahmoudjoe3.wasfa.ui.main.account.profilePostItemAdapter;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    private static final String TAG = "homey";
    HomeViewModel viewModel;
    User mUser;
    SharedViewModel sharedViewModel;
    @BindView(R.id.shimmer_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.mostCommonRecycle)
    RecyclerView mostCommonRecycle;
    private int REC_AUTH_CODE = 1;

    RecipePostAdapter adapter;


    profilePostItemAdapter profilePostItemAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        adapter = new RecipePostAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });

        profilePostItemAdapter=new profilePostItemAdapter(prevalent.COMMON_ITEM);
        mostCommonRecycle.setAdapter(profilePostItemAdapter);
        mostCommonRecycle.setHasFixedSize(true);
        viewModel.getRecipeMutableLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                profilePostItemAdapter.setRecipeList(recipes);
            }
        });

        profilePostItemAdapter.setmOnItemClickListener(new profilePostItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(getActivity(),recipe,mUser);
            }
        });

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user1) {
                mUser = user1;
            }
        });

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

        adapter.setOnProfileClickListener(new RecipePostAdapter.OnProfileClickListener() {
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
        });

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //recived data
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