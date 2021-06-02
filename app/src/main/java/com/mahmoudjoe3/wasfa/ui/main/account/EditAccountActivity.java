package com.mahmoudjoe3.wasfa.ui.main.account;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.UserPost;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.mahmoudjoe3.wasfa.ui.main.MainActivity;
import com.mahmoudjoe3.wasfa.ui.main.viewImage.ViewImageActivity;
import com.mahmoudjoe3.wasfa.viewModel.AccountViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@AndroidEntryPoint
public class EditAccountActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.acc_image)
    CircleImageView accImage;
    private static final String SHARED_PREFERENCE_NAME = "userShared";
    private Gson gson;
    UserPost user;
    Uri imageUri;
    @BindView(R.id.user_name_toolbar)
    TextView userNameToolbar;
    @BindView(R.id.edit_acc_name)
    MaterialEditText editAccName;
    @BindView(R.id.edit_acc_email)
    MaterialEditText editAccEmail;
    @BindView(R.id.edit_acc_phone)
    MaterialEditText editAccPhone;
    @BindView(R.id.edit_Bio)
    MaterialEditText editBio;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.instagram)
    MaterialEditText instagram;
    @BindView(R.id.facebook)
    MaterialEditText facebook;
    @BindView(R.id.youtube)
    MaterialEditText youtube;
    AccountViewModel accountViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        /*
         ** SharedPreference Code to get the logged in user
         */
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        gson = new Gson();
        user = gson.fromJson((sharedPreferences.getString("user", null)), UserPost.class);
        init();

    }

    private void init() {
        Glide.with(accImage.getContext()).load(user.getImageUrl()).into(accImage);
        userNameToolbar.setText(user.getName());
        editAccName.setText(user.getName());
        editAccPhone.setText(user.getPhone());
        editAccEmail.setText(user.getEmail());
        editBio.setText(user.getBio());
        if(user.getGender().equalsIgnoreCase("male")){
            male.setChecked(true);
        }else female.setChecked(true);
        if(user.getLinks()!=null) {
            if (user.getLinks().size()>=1&&user.getLinks().get(0) != null)
                facebook.setText(user.getLinks().get(0));
            if (user.getLinks().size()>=2&&user.getLinks().get(1) != null)
                instagram.setText(user.getLinks().get(1));
            if (user.getLinks().size()>=3&&user.getLinks().get(2) != null)
                youtube.setText(user.getLinks().get(2));
        }

    }

    @OnClick({R.id.edit_back, R.id.edit_save, R.id.edit_picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_back:
                finish();
                break;
            case R.id.edit_save:
                editUser();
                break;
            case R.id.edit_picture:
                pic_Edit();
                break;
        }
    }

    private void editUser() {
        user.setName(editAccName.getText().toString());
        if(imageUri!=null)
            user.setImageUrl(imageUri.toString());
        user.setBio(editBio.getText().toString());
        user.setPhone(editAccPhone.getText().toString());
        user.setEmail(editAccEmail.getText().toString());
        if(male.isChecked()){
            user.setGender("male");
        }else user.setGender("female");

        List<String>lnks=new ArrayList<>();
        lnks.add(facebook.getText().toString()+" ");
        lnks.add(instagram.getText().toString()+" " );
        lnks.add(youtube.getText().toString()+" " );
        user.setLinks(lnks);

        accountViewModel.UpdateUser(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(EditAccountActivity.this, "Profile edited succeccfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EditAccountActivity.this, MainActivity.class);
                intent.putExtra("sharedEdit",new Gson().toJson(user));
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EditAccountActivity.this, ""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void pic_Edit() {

        AlertDialog builder = new AlertDialog.Builder(this).create();
        View dialog = getLayoutInflater().inflate(R.layout.add_photo_options__dialog, null);
        builder.setView(dialog);
        builder.show();
        TextView takePhoto = dialog.findViewById(R.id.takephoto);
        TextView Gallery = dialog.findViewById(R.id.gallery);
        TextView Photo = dialog.findViewById(R.id.viewphoto);
        TextView cancel = dialog.findViewById(R.id.cancel);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCameraAndStoragePermission(2);
                builder.dismiss();
            }
        });
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCameraAndStoragePermission(1);
                builder.dismiss();
            }
        });
        Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPhoto();
                builder.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
    }

    private void viewPhoto() {
        Intent intent = new Intent(this, ViewImageActivity.class);
        ArrayList<String> imgs = new ArrayList<>();
        imgs.add(String.valueOf(imageUri));
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS, imgs);
        intent.putExtra(ViewImageActivity.IMG_POS, 0);
        startActivity(intent);
    }

    private void openGallery() {
        ImagePicker.Companion.with(this).galleryOnly()
                .crop()                    //Crop image(Optional), Check Customization for more option
                .cropOval()             //Allow dimmed layer to have a circle inside
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(prevalent.GALLERY_REQUEST_CODE);
    }

    private void openCamera() {
        ImagePicker.Companion.with(this).cameraOnly()
                .crop()                    //Crop image(Optional), Check Customization for more option
                .cropOval()                //Allow dimmed layer to have a circle inside
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(prevalent.CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == prevalent.GALLERY_REQUEST_CODE || requestCode == prevalent.CAMERA_REQUEST_CODE)
                && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).fit().centerCrop().into(accImage);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }


    @AfterPermissionGranted(prevalent.PERMISSION_CODE)
    private void RequestCameraAndStoragePermission(int i) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(EditAccountActivity.this, perms)) {
            if (i == 1) {
                openGallery();
            } else {
                openCamera();
            }
        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because " +
                            "of you want to access your storage or the camera",
                    prevalent.PERMISSION_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}