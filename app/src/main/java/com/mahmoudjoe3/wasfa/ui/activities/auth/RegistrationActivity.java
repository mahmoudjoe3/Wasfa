package com.mahmoudjoe3.wasfa.ui.activities.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.User;
import com.mahmoudjoe3.wasfa.viewModel.AuthViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.back_imageButton)
    ImageButton backImageButton;
    @BindView(R.id.fullName_textInput)
    TextInputLayout fullNameTextInput;
    @BindView(R.id.email_textInput)
    TextInputLayout emailTextInput;
    @BindView(R.id.password_textInput)
    TextInputLayout passwordTextInput;
    @BindView(R.id.phone_textInput)
    TextInputLayout phoneTextInput;
    @BindView(R.id.male_radio)
    RadioButton maleRadio;
    @BindView(R.id.female_radio)
    RadioButton femaleRadio;
    @BindView(R.id.countryCodePicker)
    CountryCodePicker countryCodePicker;
    @BindView(R.id.register_button)
    Button registerButton;

    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    AuthViewModel authViewModel;
    private String name = "", email = "", password = "", phone = "", gender = "", nationality = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        authViewModel=new ViewModelProvider(this).get(AuthViewModel.class);
        init();
    }

    private void init() {
        gson = new Gson();
        sharedPreferences = getSharedPreferences("new_user", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void initData() {
        name = fullNameTextInput.getEditText().getText().toString().trim();
        email = emailTextInput.getEditText().getText().toString().trim();
        password = passwordTextInput.getEditText().getText().toString().trim();
        phone = phoneTextInput.getEditText().getText().toString().trim();
        if(maleRadio.isChecked())
            gender = "male";
        else if (femaleRadio.isChecked())
            gender = "female";
        nationality = countryCodePicker.getSelectedCountryName();
    }

    @OnClick(R.id.back_imageButton)
    public void onBackImageButtonClicked() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClicked() {
        initData();
        boolean allValid = allIsValid();
        if(allValid) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setGender(gender);
            user.setNationality(nationality);
            user.setFollowings(new ArrayList<>());
            user.setRecipes(new ArrayList<>());
            user.setFollower(0);
            user.setImageUrl("https://metabiomedamericas.com/wp-content/uploads/2018/05/facebook-avatar.jpg");
            String userJson = gson.toJson(user);
            editor.putString("user", userJson);
            editor.apply();
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allIsValid() {
        boolean isValid = true;
        if(name.isEmpty()) {
            fullNameTextInput.getEditText().setError("Please, Enter your name");
            isValid = false;
        }
        if(email.isEmpty()) {
            emailTextInput.getEditText().setError("Please, Enter your email");
            isValid = false;
        }
        if(password.isEmpty()) {
            passwordTextInput.setError("Please, Enter your password");
            isValid = false;
        }
        if(phone.isEmpty()) {
            phoneTextInput.getEditText().setError("Please, Enter your phone number");
            isValid = false;
        }
        if(!maleRadio.isChecked() && !femaleRadio.isChecked()) {
            Toast.makeText(RegistrationActivity.this, "choose your gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}