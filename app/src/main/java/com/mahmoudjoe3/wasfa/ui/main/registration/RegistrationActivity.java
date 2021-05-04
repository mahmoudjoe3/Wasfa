package com.mahmoudjoe3.wasfa.ui.main.registration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.ui.main.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_imageButton)
    public void onBackImageButtonClicked() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClicked() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}