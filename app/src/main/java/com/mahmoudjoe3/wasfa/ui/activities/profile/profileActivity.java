package com.mahmoudjoe3.wasfa.ui.activities.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
import com.mahmoudjoe3.wasfa.ui.main.account.profilePostItemAdapter;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;
import com.mahmoudjoe3.wasfa.viewModel.InteractionsViewModel;
import com.mahmoudjoe3.wasfa.viewModel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;
import de.hdodenhof.circleimageview.CircleImageView;

@AndroidEntryPoint
public class profileActivity extends AppCompatActivity {

    public static final String USER_INTENT = "profileActivity.USER_INTENT";

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_name_toolbar)
    TextView user_name_toolbar;
    @BindView(R.id.followers)
    TextView followers;
    @BindView(R.id.posts)
    TextView posts;
    @BindView(R.id.followings)
    TextView followings;
    @BindView(R.id.user_image)
    CircleImageView user_Image;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.user_instgram)
    ImageButton userInstgram;
    @BindView(R.id.user_youtube)
    ImageButton userYoutube;
    @BindView(R.id.user_facebook)
    ImageButton userFacebook;
    @BindView(R.id.user_follow)
    TextView userFollow;
    @BindView(R.id.user_checked)
    ImageButton userChecked;
    @BindView(R.id.post_recycle)
    RecyclerView postRecycle;

    profilePostItemAdapter adapter;
    User mUser;
    ProfileViewModel profileViewModel;
    InteractionsViewModel interactionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        interactionsViewModel = new ViewModelProvider(this).get(InteractionsViewModel.class);
        adapter = new profilePostItemAdapter(prevalent.PROFILE_ITEM);

        int id =  getIntent().getIntExtra(USER_INTENT,1);
        profileViewModel.getUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for(User u:users){
                    if(u.getId()==id){
                        mUser=u;
                        Glide.with(user_Image.getContext()).load(mUser.getImageUrl())
                                .into(user_Image);
                        user_name.setText("@" + mUser.getName());
                        user_name_toolbar.setText(mUser.getName());
                        adapter.setRecipeList(mUser.getRecipes());
                        bio.setText(mUser.getBio());
                        if(mUser.getLinks()!=null) {
                            if(mUser.getLinks().get(0)==null||mUser.getLinks().get(0).isEmpty())
                                userFacebook.setVisibility(View.GONE);
                            else
                            {
                                userFacebook.setTag(mUser.getLinks().get(0));
                                userFacebook.setVisibility(View.VISIBLE);
                            }
                            if(mUser.getLinks().get(1)==null||mUser.getLinks().get(1).isEmpty())
                                userInstgram.setVisibility(View.GONE);
                            else
                            {
                                userInstgram.setTag(mUser.getLinks().get(1));
                                userInstgram.setVisibility(View.VISIBLE);
                            }
                            if(mUser.getLinks().get(2)==null||mUser.getLinks().get(2).isEmpty())
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

                        followers.setText(mUser.getFollower()+"");
                        followings.setText(mUser.getFollowings().size()+"");
                        posts.setText(mUser.getRecipes().size()+"");

                        adapter.setRecipeList(mUser.getRecipes());
                        break;
                    }
                }
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
                interactionsViewModel.insertInteraction(new Interaction(recipe.getUserName(),recipe.getUserProfileThumbnail(), "Follow"));

            }
        });
        MyLogic.setOnProfileClickListener(new MyLogic.OnProfileClickListener() {

            @Override
            public void onAddComment(Comment comment) {
                interactionsViewModel.insertInteraction(new Interaction(comment.getUsername(),comment.getUserImageUrl(),"Commented On"));
            }
        });




        postRecycle.setAdapter(adapter);
        postRecycle.setHasFixedSize(true);


        adapter.setmOnItemClickListener(new profilePostItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(profileActivity.this, recipe, mUser);
            }
        });
    }

    @OnClick({R.id.back, R.id.user_instgram, R.id.user_youtube, R.id.user_facebook, R.id.user_follow, R.id.user_image, R.id.user_checked})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_follow:
                userFollow.setVisibility(View.GONE);
                userChecked.setVisibility(View.VISIBLE);
                interactionsViewModel.insertInteraction(new Interaction(mUser.getName(), mUser.getImageUrl(), "Follow"));

                break;
            case R.id.user_checked:
                userFollow.setVisibility(View.VISIBLE);
                userChecked.setVisibility(View.GONE);
                break;
            case R.id.back:
                profileActivity.this.onBackPressed();
                finish();
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
            case R.id.user_image:
                showImage(List.of(mUser.getImageUrl()), 0);
                break;
        }
    }

    private void openLink(String tag) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(tag));
        startActivity(intent);
    }

    private void showImage(List<String> imgUrls, int pos) {
        Intent intent = new Intent(this, ViewImageActivity.class);
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, new ArrayList<>(imgUrls));
        intent.putExtra(ViewImageActivity.IMG_POS, pos);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}