package com.mahmoudjoe3.wasfaty.ui.main.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.Comment;
import com.mahmoudjoe3.wasfaty.pojo.CommentPost;
import com.mahmoudjoe3.wasfaty.pojo.Following;
import com.mahmoudjoe3.wasfaty.pojo.Interaction;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.prevalent.prevalent;
import com.mahmoudjoe3.wasfaty.ui.activities.profile.profileActivity;
import com.mahmoudjoe3.wasfaty.ui.main.MainActivity;
import com.mahmoudjoe3.wasfaty.ui.main.account.profilePostItemAdapter;
import com.mahmoudjoe3.wasfaty.ui.main.home.comment.CommentAdapter;
import com.mahmoudjoe3.wasfaty.ui.main.viewImage.ViewImageActivity;
import com.mahmoudjoe3.wasfaty.viewModel.HomeViewModel;
import com.mahmoudjoe3.wasfaty.viewModel.InteractionsViewModel;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;
    @BindView(R.id.mostCommon_shimmer)
    ShimmerFrameLayout mostCommon_shimmer;
    @BindView(R.id.paging_progress)
    ProgressBar progress;
    @BindView(R.id.mostCommonRecycle)
    RecyclerView mostCommonRecycle;//home_nestedScroll
    @BindView(R.id.home_nestedScroll)
    NestedScrollView home_nestedScroll;
    @BindView(R.id.interaction_label)
    TextView mostCommonLabel;
    private int REC_AUTH_CODE = 1;

    RecipePostAdapter adapter;


    profilePostItemAdapter mostCommonAdapter;

    private List<Recipe> recipeList;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<Integer> loveList;

    int page = 1, limit = 10;

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


        return view;

    }

    private void setupMostCommonAdapter() {
        mostCommonAdapter = new profilePostItemAdapter(prevalent.COMMON_ITEM);
        mostCommonRecycle.setAdapter(mostCommonAdapter);

        mostCommonRecycle.setHasFixedSize(true);
        getMostCommonRecipes();
        mostCommonAdapter.setmOnItemClickListener(new profilePostItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(getActivity(), recipe, mUser);
            }
        });
    }

    private void setupRecipeAdapter() {
        adapter = new RecipePostAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        adapter.setOnOpenProfileListener(new RecipePostAdapter.OpenProfileListener() {
            @Override
            public void intentToAccount() {
                ((MainActivity) getActivity()).selectFragByiId(R.id.menu_account);
            }
        });
        getRecipes();
    }

    private void getRecipes() {
        homeViewModel.getRangedRecipes(page, limit).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    List<Recipe> posts = Recipe.parseRecipeJson(response.body().toString());
                    Collections.reverse(posts);
                    List<Recipe> temp2 = new ArrayList<>();
                    for (Recipe r : posts) {
                        if (r.getPrivacy() != null && !r.getPrivacy().equalsIgnoreCase("public"))
                            continue;
                        //if(r.getUserId()==mUser.getId())
                        temp2.add(r);
                    }
                    progress.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.setVisibility(View.GONE);
                    adapter.setuser(mUser);
                    adapter.addAll(temp2);
                    adapter.setFollowing(mUser.getFollowings());
                } else {
                    adapter.setRecipes(new ArrayList<>());
                    Toast.makeText(getActivity(), "Error Response code " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                adapter.setRecipes(new ArrayList<>());
            }
        });
    }

    private void getMostCommonRecipes() {
        homeViewModel.getMostCommonRecipes(limit).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    List<Recipe> posts = Recipe.parseRecipeJson(response.body().toString());
                    mostCommonRecycle.setVisibility(View.VISIBLE);
                    mostCommon_shimmer.stopShimmer();
                    mostCommon_shimmer.setVisibility(View.GONE);
                    if(!posts.isEmpty()) {
                        mostCommonLabel.setVisibility(View.VISIBLE);
                    }else {

                    }
                    mostCommonAdapter.setRecipeList(posts);

                } else {
                    mostCommonAdapter.setRecipeList(new ArrayList<>());
                    Toast.makeText(getActivity(), "Error Response code " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mostCommonAdapter.setRecipeList(new ArrayList<>());
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecipeAdapter();
        setupMostCommonAdapter();

        shimmer.startShimmer();
        home_nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    progress.setVisibility(View.VISIBLE);
                    getRecipes();
                }
            }
        });

        mostCommon_shimmer.startShimmer();


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
            public void onshare(Recipe recipe, ImageView imageView) {
                shareImage(imageView);
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Shared"));
                homeViewModel.share(recipe.getId()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onlove(Recipe recipe, TextView post_love_number, ImageView post_love_number_ic) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Loved"));
                homeViewModel.love(recipe.getId()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {

                            post_love_number.setVisibility(View.VISIBLE);
                            post_love_number_ic.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onDislove(Recipe recipe, TextView post_love_number, ImageView post_love_number_ic) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "DisLoved"));
                homeViewModel.disLove(recipe.getId()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {

                            int x=Integer.parseInt(post_love_number.getText().toString());
                            if(x==0) {
                                post_love_number.setVisibility(View.GONE);
                                post_love_number_ic.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onfollow(Recipe recipe) {
                homeViewModel.follow(new Following.followingPost(0, mUser.getId(), recipe.getUserId())).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {
                            interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Follow"));
                        } else
                            Toast.makeText(getActivity(), "response code " + response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        MyLogic.setOninteractionClickListener(new MyLogic.OninteractionClickListener() {
            @Override
            public void onshare(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Shared"));
            }

            @Override
            public void onlove(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Loved"));
                homeViewModel.love(recipe.getId()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onDislove(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "DisLoved"));
                homeViewModel.disLove(recipe.getId()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onfollow(Recipe recipe) {
                homeViewModel.follow(new Following.followingPost(0, mUser.getId(), recipe.getUserId())).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() >= 200 && response.code() < 300) {
                            interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Follow"));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        MyLogic.commentAdapter.setOnAddCommentListenner(new CommentAdapter.OnAddCommentListenner() {
            @Override
            public void onAdded(Comment comment) {
                double commentPolarity = 0;
                commentPolarity = BertClassify(comment.getCommentText());
                CommentPost commentPost = new CommentPost(comment, commentPolarity);
                homeViewModel.postComment(commentPost).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() >= 200 && response.code() < 300) {
                            int i=0;
                            Recipe recipe1 = new Recipe();
                            List<Recipe> recipeList=adapter.getRecipes();
                            for(Recipe r:recipeList){
                                if(r.getId()==comment.getRecipeId()){
                                    recipe1=r;
                                    break;
                                }
                                i++;
                            }
                            List<Comment> comments=recipe1.getComments();
                            comments.add(comment);
                            recipe1.setComments(comments);
                            recipeList.set(i,recipe1);
                            adapter.notifyItemChanged(i);
                            interactionsViewModel.insertInteraction(new Interaction(comment.getUsername(), comment.getUserImageUrl(), "Commented On"));
                        } else {
                            Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        adapter.setOnProfileClickListener(new RecipePostAdapter.OnProfileClickListener() {
            @Override
            public void onClick(int userid) {
                Intent intent = new Intent(getActivity(), profileActivity.class);
                intent.putExtra(profileActivity.USER_INTENT, userid);
                startActivity(intent);
            }
        });

    }

    public double BertClassify(String commentText) {
        // Initialization
        BertNLClassifier classifier2 = null;
        try {
            classifier2 = BertNLClassifier.createFromFile(getActivity(), prevalent.modelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BertNLClassifier finalClassifier1 = classifier2;
        List<Category> results = finalClassifier1.classify(commentText);
        Log.d("tag###", "onClick: " + commentText + results.get(0).getLabel() + results.get(0).getScore()
                + results.get(1).getLabel() + results.get(1).getScore());
        double neg = results.get(0).getScore();
        double pos = results.get(1).getScore();
        return (neg > pos) ? -1 * neg : pos;
    }

    private void shareImage(ImageView uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, MyLogic.getImageUri(getActivity(), getImageBitmap(uri)));
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Wasfa:: ");
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

    }

    private Bitmap getImageBitmap(ImageView img) {
        ImageView imageView = img;
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        return drawable.getBitmap();
    }

    private void showImage(List<String> imgUrls, int pos) {
        Intent intent = new Intent(getActivity(), ViewImageActivity.class);
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, new ArrayList<>(imgUrls));
        intent.putExtra(ViewImageActivity.IMG_POS, pos);
        startActivity(intent);
    }

    public static interface onRecipeChangeListener{
        public void onChange(Comment comment);
    }
    static onRecipeChangeListener recipeChangeListener;

    public static void setRecipeChangeListener(onRecipeChangeListener recipeChangeListener) {
        recipeChangeListener = recipeChangeListener;
    }
}