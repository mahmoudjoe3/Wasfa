package com.mahmoudjoe3.wasfa.ui.main.account;

import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Comment;
import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.mahmoudjoe3.wasfa.ui.activities.auth.LoginActivity;
import com.mahmoudjoe3.wasfa.viewModel.AccountViewModel;
import com.mahmoudjoe3.wasfa.viewModel.InteractionsViewModel;
import com.mahmoudjoe3.wasfa.viewModel.SharedViewModel;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;

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
    SharedViewModel sharedViewModel;
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
        MyLogic.setOnProfileClickListener(new MyLogic.OnProfileClickListener() {
            @Override
            public void onAddComment(Comment comment) {
                interactionsViewModel.insertInteraction(new Interaction(comment.getUsername(), comment.getUserImageUrl(), "Commented On"));
            }
        });

        postRecycle.setAdapter(adapter);
        postRecycle.setHasFixedSize(true);


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

        sharedPreferences = getActivity().getSharedPreferences("userId", MODE_PRIVATE);
        accountViewModel=new ViewModelProvider(this).get(AccountViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getUser(sharedPreferences.getInt("id",0)).observe(getActivity(), new Observer<UserPost>() {
            @Override
            public void onChanged(UserPost userPost) {
                //recived data
                mUser=userPost;
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
                        List<Recipe> recipes=Recipe.parseJson(response.body().toString());
                        adapter.setRecipeList(recipes);
                        posts.setText(recipes.size()+"");
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                followers.setText(mUser.getFollowersCount()+"");
                followings.setText(mUser.getFollowings().size()+"");
            }
        });

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
                showImage(List.of(mUser.getImageUrl()), 0);
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
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("new_user", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("remember_me",false);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


}