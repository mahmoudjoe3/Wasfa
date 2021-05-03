package com.mahmoudjoe3.wasfa.ui.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.ui.main.MainActivity;
import com.mahmoudjoe3.wasfa.ui.main.registration.RegistrationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.user_name_textInput)
    TextInputLayout userNameTextInput;
    @BindView(R.id.password_textInput)
    TextInputLayout passwordTextInput;
    @BindView(R.id.forgetPass_textView)
    TextView forgetPassTextView;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.facebook_imageView)
    ImageView facebookImageView;
    @BindView(R.id.google_imageView)
    ImageView googleImageView;
    @BindView(R.id.register_textView)
    TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.forgetPass_textView)
    public void onForgetPassTextViewClicked() {
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.register_textView)
    public void onRegisterTextViewClicked() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }
}