package com.mahmoudjoe3.wasfa.ui.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.viewModel.AuthViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;

    private Gson gson;

    AuthViewModel authViewModel;
    private String name = "", email = "", password = "", phone = "", gender = "", nationality = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        init();
    }

    private void init() {
        gson = new Gson();
    }

    private void initData() {
        name = fullNameTextInput.getEditText().getText().toString().trim();
        email = emailTextInput.getEditText().getText().toString().trim();
        password = passwordTextInput.getEditText().getText().toString().trim();
        phone = phoneTextInput.getEditText().getText().toString().trim();
        if (maleRadio.isChecked())
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
        if (allValid) {
            progressBar.setVisibility(View.VISIBLE);
            UserPost user = new UserPost(0, name, email.toLowerCase(), password, gender, phone, nationality);
            user.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasfa-9891c.appspot.com/o/userImages%2FDefault-welcomer.png?alt=media&token=1255372e-69bc-4599-967b-96a76223dc2e");
            //post in api
            authViewModel.Register(user).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.i("Registeration Activity", "##0" + response.code());
                    try {
                        String found = new JSONObject(response.body().toString()).getString("found");
                        if (found.toLowerCase().contains("email"))
                            Toast.makeText(RegistrationActivity.this, "Invalied email", Toast.LENGTH_SHORT).show();
                        else if (found.toLowerCase().contains("phone"))
                            Toast.makeText(RegistrationActivity.this, "Invalied phone", Toast.LENGTH_SHORT).show();
                        if (found.toLowerCase().contains("successful") && response.code() >= 200 && response.code() <= 299) {
                            Toast.makeText(RegistrationActivity.this, "Registeration SucessFully", Toast.LENGTH_SHORT).show();
                            RegistrationActivity.this.onBackPressed();
                        } else if (response.code() >= 400 && response.code() <= 499) {
                            Toast.makeText(RegistrationActivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                        } else if (response.code() >= 500 && response.code() <= 599) {
                            Toast.makeText(RegistrationActivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allIsValid() {
        boolean isValid = true;
        if (name.isEmpty()) {
            fullNameTextInput.getEditText().setError("Please, Enter your name");
            isValid = false;
        }
        if (email.isEmpty()) {
            emailTextInput.getEditText().setError("Please, Enter your email");
            isValid = false;
        }
        if (password.isEmpty()) {
            passwordTextInput.setError("Please, Enter your password");
            isValid = false;
        }
        if (phone.isEmpty()) {
            phoneTextInput.getEditText().setError("Please, Enter your phone number");
            isValid = false;
        }
        if (!maleRadio.isChecked() && !femaleRadio.isChecked()) {
            Toast.makeText(RegistrationActivity.this, "choose your gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}