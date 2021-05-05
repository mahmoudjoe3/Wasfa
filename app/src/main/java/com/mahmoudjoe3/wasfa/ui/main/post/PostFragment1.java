package com.mahmoudjoe3.wasfa.ui.main.post;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.opensooq.supernova.gligar.GligarPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class PostFragment1 extends Fragment {

    @BindView(R.id.userProfile_imageView)
    CircleImageView userProfileImageView;
    @BindView(R.id.userName_textView)
    TextView userNameTextView;
    private PostSharedViewModel postSharedViewModel;
    private static final int PICKER_REQUEST_CODE = 1;
    private TextView nextTextView, privacyTextView;
    private EditText recipeDescriptionEditText;
    private ImageButton pickImageButton;
    private RecyclerView imageRecyclerView;
    private ImageAdapter imageAdapter;
    private LinearLayout privacyLayout;
    private ImageView privacyImageView;
    private List<String> imageUrls;
    private Recipe recipe;
    private CountryCodePicker countryCodePicker;
    private View view;

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private User user;

    public PostFragment1() {
    }

    private static PostFragment1 postFragment1;

    public static PostFragment1 getInstance() {
        if (postFragment1 == null) {
            postFragment1 = new PostFragment1();
        }
        return postFragment1;
    }

    public static void removeFragment() {
        postFragment1 = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_fragement1, container, false);
        ButterKnife.bind(this, view);
        init();
        initRecyclerView();
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GligarPicker().requestCode(PICKER_REQUEST_CODE).withFragment(PostFragment1.this).show();

            }
        });


        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSet = setPostData();
                if (isSet) {
                    getParentFragmentManager().beginTransaction().replace(
                            R.id.fragment_container, PostFragment2.getInstance()
                    ).commit();
                } else {
                    Toast.makeText(getActivity(), "Complete All the recipe data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.privace_bottom_sheet_layout,
                        (CardView) getActivity().findViewById(R.id.bottomSheetContainer));
                final RadioButton publicRadioButton = bottomSheetView.findViewById(R.id.public_rb);
                final RadioButton followerRadioButton = bottomSheetView.findViewById(R.id.follower_rb);
                if (privacyTextView.getText().toString().equals("Public")) {
                    publicRadioButton.setChecked(true);
                } else {
                    followerRadioButton.setChecked(true);
                }

                publicRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (publicRadioButton.isChecked()) {
                            if (followerRadioButton.isChecked()) {
                                followerRadioButton.setChecked(false);
                            }
                            privacyTextView.setText(R.string.public_tag);
                            privacyImageView.setImageResource(R.drawable.ic_baseline_public_24);
                        }
                    }
                });

                followerRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (followerRadioButton.isChecked()) {
                            if (publicRadioButton.isChecked()) {
                                publicRadioButton.setChecked(false);
                            }
                            privacyTextView.setText(R.string.followers_only);
                            privacyImageView.setImageResource(R.drawable.ic_follower);
                        }

                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        postSharedViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe r) {
                if (r != null) {
                    recipe = r;
                    recipeDescriptionEditText.setText(recipe.getDescription());
                    imageAdapter.setImageList(recipe.getImgUrls());
                    imageUrls = recipe.getImgUrls();
                }
            }
        });

        return view;
    }

    private boolean setPostData() {
        if (!recipeDescriptionEditText.getText().toString().isEmpty() && imageUrls.size() > 0) {
            recipe.setUserId(1);
            recipe.setDescription(recipeDescriptionEditText.getText().toString());
            recipe.setImgUrls(imageUrls);
            recipe.setNationality(countryCodePicker.getSelectedCountryName());
            recipe.setUserName(user.getName());
            recipe.setUserProfileThumbnail(user.getImageUrl());
            postSharedViewModel.setRecipe(recipe);
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == PICKER_REQUEST_CODE) {
            String[] pathsList = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            Collections.addAll(imageUrls, pathsList);
            imageAdapter.setImageList(imageUrls);
            imageAdapter.setOnItemClickListener(new ImageAdapter.onItemClickListener() {
                @Override
                public void onRemoveClick(int position) {
                    imageAdapter.removeAt(position);
                }
            });
        }
    }

    private void init() {
        postSharedViewModel = new ViewModelProvider(getActivity()).get(PostSharedViewModel.class);
        recipeDescriptionEditText = view.findViewById(R.id.recipeDescription_editText);
        pickImageButton = view.findViewById(R.id.pickImage_imageButton);
        nextTextView = view.findViewById(R.id.next_textView);
        imageRecyclerView = view.findViewById(R.id.image_recyclerView);
        privacyLayout = view.findViewById(R.id.privacy_layout);
        privacyTextView = view.findViewById(R.id.privacy_textView);
        privacyImageView = view.findViewById(R.id.privacy_imageView);
        countryCodePicker = view.findViewById(R.id.countryCodePicker);
        imageUrls = new ArrayList<>();
        recipe = new Recipe();
        sharedPreferences = getActivity().getSharedPreferences("new_user", Context.MODE_PRIVATE);
        gson = new Gson();
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        userNameTextView.setText(user.getName());
        Picasso.get().load(user.getImageUrl()).fit().centerCrop().into(userProfileImageView);
    }

    private void initRecyclerView() {
        imageAdapter = new ImageAdapter();
        imageRecyclerView.setAdapter(imageAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        imageRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    @OnClick(R.id.closeShare_imageButton)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }
}
