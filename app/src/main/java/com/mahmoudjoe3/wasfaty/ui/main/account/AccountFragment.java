package com.mahmoudjoe3.wasfaty.ui.main.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.Interaction;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.prevalent.prevalent;
import com.mahmoudjoe3.wasfaty.ui.activities.auth.LoginActivity;
import com.mahmoudjoe3.wasfaty.viewModel.AccountViewModel;
import com.mahmoudjoe3.wasfaty.viewModel.InteractionsViewModel;
import com.mahmoudjoe3.wasfaty.ui.main.viewImage.ViewImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

@AndroidEntryPoint
public class AccountFragment extends Fragment {
    AccountViewModel accountViewModel;
    InteractionsViewModel interactionsViewModel;
    @BindView(R.id.user_image)
    CircleImageView user_image;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.followers)
    TextView followers;
    @BindView(R.id.posts)
    TextView posts;
    @BindView(R.id.followings)
    TextView followings;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.user_name_toolbar)
    TextView userNameToolbar;
    @BindView(R.id.user_instgram)
    ImageButton userInstgram;
    @BindView(R.id.user_youtube)
    ImageButton userYoutube;
    @BindView(R.id.user_facebook)
    ImageButton userFacebook;
    @BindView(R.id.post_recycle)
    RecyclerView postRecycle;

    profilePostItemAdapter adapter;
    UserPost mUser;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "userShared";
    private Gson gson;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        // Inflate the layout for this fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        interactionsViewModel = new ViewModelProvider(this).get(InteractionsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        adapter = new profilePostItemAdapter(prevalent.PROFILE_ITEM);


        MyLogic.setOninteractionClickListener(new MyLogic.OninteractionClickListener() {
            @Override
            public void onshare(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Shared"));

            }

            @Override
            public void onlove(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Loved"));

            }

            @Override
            public void onDislove(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "DisLoved"));

            }

            @Override
            public void onfollow(Recipe recipe) {
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(), recipe.getUserProfileThumbnail(), "Follow"));

            }
        });
        /*
        MyLogic.setOnReviewListener(new MyLogic.OnReviewListener() {
            @Override
            public void onReview(Comment comment) {
                interactionsViewModel.insertInteraction(new Interaction(comment.getUsername(), comment.getUserImageUrl(), "Commented On"));
            }
        });*/

        postRecycle.setAdapter(adapter);
        postRecycle.setHasFixedSize(true);


        adapter.setmOnItemDeletedListener(new profilePostItemAdapter.OnItemDeletedListener() {
            @Override
            public void onLongClick(Recipe recipe, int position) {
                new AlertDialog.Builder(getActivity()).setMessage("Do you want to Delete This Recipe?")
                        .setTitle("Delete Recipe").setCancelable(true)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                accountViewModel.deleteRecipe(recipe.getId()).enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        if(response.code()>=200&&response.code()<300){
                                            Toast.makeText(getActivity(), "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
                                            adapter.remove(recipe,position);
                                            int p=Integer.parseInt(posts.getText().toString());
                                            posts.setText((p-1)+"");
                                            dialog.dismiss();
                                        }else {
                                            Toast.makeText(getActivity(), "Faild to delete recipe response code "+response.code(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Faild to delete recipe Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create()
                        .show();
            }
        });

        adapter.setmOnItemClickListener(new profilePostItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(getActivity(), recipe, mUser);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountViewModel=new ViewModelProvider(this).get(AccountViewModel.class);

        /*
         ** SharedPreference Code to get the logged in user
         */
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        gson = new Gson();
        mUser = gson.fromJson((sharedPreferences.getString("user", null)), UserPost.class);

        accountViewModel.getUserBy(mUser.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()>=200&&response.code()<300){
                    mUser=UserPost.parseUserRespone(response.body().toString());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user",new Gson().toJson(mUser));
                    editor.commit();
                    followers.setText(mUser.getFollowersCount()+"");
                    followings.setText(mUser.getFollowings().size()+"");
                }else Toast.makeText(getActivity(), "response code " +response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "response code " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(user_image.getContext()).load(mUser.getImageUrl())
                .into(user_image);
        user_name.setText("@" + mUser.getName());
        userNameToolbar.setText(mUser.getName());
        bio.setText(mUser.getBio());
        if(mUser.getLinks()!=null) {
            if(mUser.getLinks().size()<1||mUser.getLinks().get(0)==null||mUser.getLinks().get(0).isEmpty())
                userFacebook.setVisibility(View.GONE);
            else
            {
                userFacebook.setTag(mUser.getLinks().get(0));
                userFacebook.setVisibility(View.VISIBLE);
            }
            if(mUser.getLinks().size()<2||mUser.getLinks().get(1)==null||mUser.getLinks().get(1).isEmpty())
                userInstgram.setVisibility(View.GONE);
            else
            {
                userInstgram.setTag(mUser.getLinks().get(1));
                userInstgram.setVisibility(View.VISIBLE);
            }
            if(mUser.getLinks().size()<3||mUser.getLinks().get(2)==null||mUser.getLinks().get(2).isEmpty())
                userYoutube.setVisibility(View.GONE);
            else
            {
                userYoutube.setTag(mUser.getLinks().get(2));
                userYoutube.setVisibility(View.VISIBLE);
            }

        }else {
            userFacebook.setVisibility(View.GONE);
            userInstgram.setVisibility(View.GONE);
            userYoutube.setVisibility(View.GONE);
        }
        accountViewModel.getUserRecipes(mUser.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code()>=200&&response.code()<300) {
                    List<Recipe> recipes = Recipe.parseRecipeJson(response.body().toString());
                    adapter.setRecipeList(recipes);
                    posts.setText(recipes.size() + "");
                }else {
                    adapter.setRecipeList(new ArrayList<>());
                    Toast.makeText(getActivity(), "Error Response code "+response.code(), Toast.LENGTH_SHORT).show();
                    posts.setText( "0");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.setRecipeList(new ArrayList<>());
                posts.setText( "");
            }
        });


        followers.setText(mUser.getFollowersCount()+"");
        followings.setText(mUser.getFollowings().size()+"");
    }

    private void showImage(List<String> imgUrls, int pos) {
        Intent intent = new Intent(getActivity(), ViewImageActivity.class);
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, new ArrayList<>(imgUrls));
        intent.putExtra(ViewImageActivity.IMG_POS, pos);
        startActivity(intent);
    }


    @OnClick({R.id.user_editProfile, R.id.user_instgram, R.id.user_youtube, R.id.user_facebook, R.id.user_image, R.id.back,R.id.user_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_editProfile:
                startActivity(new Intent(getActivity(), EditAccountActivity.class));
                break;
            case R.id.user_instgram:
                openLink(userInstgram.getTag().toString());
                break;
            case R.id.user_youtube:
                openLink(userYoutube.getTag().toString());
                break;
            case R.id.user_facebook:
                openLink(userFacebook.getTag().toString());
                break;
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.user_image:
                List<String> list=new ArrayList<>();
                list.add(mUser.getImageUrl());
                showImage(list, 0);
                break;
            case R.id.user_logout:
                logout();
        }
    }

    private void openLink(String tag) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(tag));
        startActivity(intent);
    }

    private void logout() {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userShared", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("remember_me",false);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


}