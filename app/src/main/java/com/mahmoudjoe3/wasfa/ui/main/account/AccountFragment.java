package com.mahmoudjoe3.wasfa.ui.main.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.mahmoudjoe3.wasfa.ui.activities.profile.EditProfileActivity;
import com.mahmoudjoe3.wasfa.ui.activities.profile.profileActivity;
import com.mahmoudjoe3.wasfa.ui.main.SharedViewModel;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {
    AccountViewModel viewModel;
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
    User mUser;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

        viewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser=user;
                Glide.with(user_image.getContext()).load(user.getImageUrl())
                        .into(user_image);
                user_name.setText("@"+user.getName());
                userNameToolbar.setText(user.getName());
                //userFacebook.setTag(user.getLinks().get());
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
                MyLogic.init_post_details_sheet_dialog(getActivity(),recipe,mUser);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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


    @OnClick({R.id.user_editProfile, R.id.user_instgram, R.id.user_youtube, R.id.user_facebook, R.id.user_image,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_editProfile:
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            case R.id.user_instgram:
                break;
            case R.id.user_youtube:
                break;
            case R.id.user_facebook:
                break;
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.user_image:
                showImage(List.of("https://i.pinimg.com/1200x/11/c7/35/11c7359cc1bf8d43011a58c0b9fe1ef2.jpg"), 0);
                break;
        }
    }


}