package com.mahmoudjoe3.wasfa.ui.activities.profile;

import android.content.Intent;
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
import com.mahmoudjoe3.wasfa.pojo.Interaction;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.mahmoudjoe3.wasfa.ui.main.account.profilePostItemAdapter;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {


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
    ProfileViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel =new ViewModelProvider(this).get(ProfileViewModel.class);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        viewModel.getUserLiveData().observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        mUser=user;
                        Glide.with(user_Image.getContext()).load(user.getImageUrl())
                                .into(user_Image);
                        user_name.setText("@"+user.getName());
                        user_name_toolbar.setText(user.getName());
                        //userFacebook.setTag(user.getLinks().get());
                    }
                });


        adapter=new profilePostItemAdapter(prevalent.PROFILE_ITEM);
        postRecycle.setAdapter(adapter);
        postRecycle.setHasFixedSize(true);
        viewModel.getRecipeMutableLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipeList(recipes);
            }
        });

        adapter.setmOnItemClickListener(new profilePostItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                MyLogic.init_post_details_sheet_dialog(profileActivity.this,recipe,mUser);
            }
        });
    }

    @OnClick({R.id.back, R.id.user_instgram, R.id.user_youtube, R.id.user_facebook, R.id.user_follow, R.id.user_image, R.id.user_checked})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_follow:
                userFollow.setVisibility(View.GONE);
                userChecked.setVisibility(View.VISIBLE);
                viewModel.insertInteraction(new Interaction(mUser.getName(),mUser.getImageUrl(), "Follow"));

                break;
            case R.id.user_checked:
                userFollow.setVisibility(View.VISIBLE);
                userChecked.setVisibility(View.GONE);
                break;
            case R.id.back:
                profileActivity.this.onBackPressed();
                break;
            case R.id.user_instgram:
                break;
            case R.id.user_youtube:
                break;
            case R.id.user_facebook:
                break;
            case R.id.user_image:
                showImage(List.of("https://i.pinimg.com/1200x/11/c7/35/11c7359cc1bf8d43011a58c0b9fe1ef2.jpg"), 0);
                break;
        }
    }

    private void showImage(List<String> imgUrls, int pos) {
        Intent intent = new Intent(this, ViewImageActivity.class);
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, new ArrayList<>(imgUrls));
        intent.putExtra(ViewImageActivity.IMG_POS, pos);
        startActivity(intent);
    }
}