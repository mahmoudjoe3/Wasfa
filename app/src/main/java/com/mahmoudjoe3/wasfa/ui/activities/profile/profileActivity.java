package com.mahmoudjoe3.wasfa.ui.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mahmoudjoe3.wasfa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {


    @BindView(R.id.acc_name)
    TextView accName;
    @BindView(R.id.followers)
    TextView followers;
    @BindView(R.id.posts)
    TextView posts;
    @BindView(R.id.followings)
    TextView followings;
    @BindView(R.id.acc_image)
    CircleImageView accImage;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.bio)
    TextView bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.back, R.id.instgram, R.id.youtube, R.id.facebook, R.id.follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.follow:
                break;
            case R.id.back:
                finish();
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