package com.mahmoudjoe3.wasfaty.ui.activities.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.ui.main.MainActivity;
import com.mahmoudjoe3.wasfaty.viewModel.AuthViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCE_NAME = "userShared";
    @BindView(R.id.user_name_textInput)
    TextInputLayout userNameTextInput;
    @BindView(R.id.password_textInput)
    TextInputLayout passwordTextInput;
    @BindView(R.id.forgetPass_textView)
    TextView forgetPassTextView;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.register_textView)
    TextView registerTextView;
    @BindView(R.id.remember_checkBox)
    CheckBox rememberCheckBox;
    @BindView(R.id.google_sign)
    Button googleSign;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private AuthViewModel authViewModel;
    private String userName = "", passwrod = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Boolean remember = sharedPreferences.getBoolean("remember_me", false);
        if (remember) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.forgetPass_textView)
    public void onForgetPassTextViewClicked() {
    }


    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        boolean complete = initData();
        if (complete) {
            progressBar.setVisibility(View.VISIBLE);
            authViewModel.login(userName.toLowerCase()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    UserPost user = UserPost.parseUserRespone(response.body().toString());
                    if (user != null && response.code() >= 200 && response.code() <= 299) {
                        if (user.getPassword().equals(passwrod)) {
                            if (rememberCheckBox.isChecked()) {
                                editor.putBoolean("remember_me", true);
                            } else {
                                editor.putBoolean("remember_me", false);
                            }
                            editor.putString("user", new Gson().toJson(user));
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    } else
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    private Boolean initData() {
        boolean complete = true;
        userName = userNameTextInput.getEditText().getText().toString().trim();
        passwrod = passwordTextInput.getEditText().getText().toString().trim();
        if (userName.isEmpty()) {
            userNameTextInput.getEditText().setError("Please, Enter your user name");
            complete = false;
        }
        if (passwrod.isEmpty()) {
            passwordTextInput.setError("Please, Enter your password");
            complete = false;
        }
        return complete;
    }

    @OnClick(R.id.register_textView)
    public void onRegisterTextViewClicked() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        //finish();
    }

    @OnClick(R.id.google_sign)
    public void onViewClicked() {
    }
}