package com.mahmoudjoe3.wasfa.ui.main.viewImage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mahmoudjoe3.wasfa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageFragment extends Fragment {


    @BindView(R.id.image_view)
    ImageView imageView;
    private static final String ARG_PARAM1_uri = "ARG_PARAM1_uri";
    private String uri;

    public static ViewImageFragment newInstance(String uri) {
        ViewImageFragment fragment = new ViewImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1_uri, uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uri = getArguments().getString(ARG_PARAM1_uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_image, container, false);
        ButterKnife.bind(this, view);
        Glide.with(imageView.getContext())
                .load(uri)
                .into(imageView);
        return view;
    }
}