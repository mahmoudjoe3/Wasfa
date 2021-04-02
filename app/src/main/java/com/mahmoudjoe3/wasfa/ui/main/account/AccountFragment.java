package com.mahmoudjoe3.wasfa.ui.main.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.ui.activities.profile.EditProfileActivity;
import com.mahmoudjoe3.wasfa.ui.main.SharedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {
    AccountViewModel viewModel;
    SharedViewModel sharedViewModel;
    @BindView(R.id.acc_image)
    CircleImageView accImage;
    @BindView(R.id.acc_name)
    TextView accName;
    @BindView(R.id.followers)
    TextView followers;
    @BindView(R.id.posts)
    TextView posts;
    @BindView(R.id.followings)
    TextView followings;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.bio)
    TextView bio;

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


    @OnClick({R.id.editProfile, R.id.instgram, R.id.youtube, R.id.facebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editProfile:
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            case R.id.instgram:
                break;
            case R.id.youtube:
                break;
            case R.id.facebook:
                break;
        }
    }
}