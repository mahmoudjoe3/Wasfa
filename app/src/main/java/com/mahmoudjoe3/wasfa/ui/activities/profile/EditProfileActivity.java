package com.mahmoudjoe3.wasfa.ui.activities.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.drjacky.imagepicker.ImagePicker;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.mahmoudjoe3.wasfa.ui.main.home.ViewImageActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class EditProfileActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.acc_image)
    CircleImageView accImage;
    @BindView(R.id.edit_acc_name)
    EditText editAccName;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_Bio)
    EditText editBio;
    @BindView(R.id.instagram)
    EditText instagram;
    @BindView(R.id.facebook)
    EditText facebook;
    @BindView(R.id.youtube)
    EditText youtube;
    @BindView(R.id.ViewImage)
    ImageView Viewphoto;

    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.edit_back, R.id.edit_save, R.id.edit_picture, R.id.ViewImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_back:
                finish();
                break;
            case R.id.edit_save:

                break;
            case R.id.edit_picture:
                pic_Edit();
                break;
            case R.id.ViewImage:
                HideImage();
                break;
        }
    }

    private void HideImage() {
        Viewphoto.setVisibility(View.GONE);
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
        Intent intent=new Intent(this, ViewImageActivity.class);
        ArrayList<String > imgs=new ArrayList<>();
        imgs.add(String.valueOf(imageUri));
        intent.putStringArrayListExtra(ViewImageActivity.IMG_URLS,imgs);
        intent.putExtra(ViewImageActivity.IMG_POS,0);
        startActivity(intent);
    }

    private void openGallery() {
        ImagePicker.Companion.with(this).galleryOnly()
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .cropOval()             //Allow dimmed layer to have a circle inside
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(prevalent.GALLERY_REQUEST_CODE);
    }

    private void openCamera() {
        ImagePicker.Companion.with(this).cameraOnly()
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .cropOval()	    		//Allow dimmed layer to have a circle inside
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(prevalent.CAMERA_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == prevalent.GALLERY_REQUEST_CODE||requestCode==prevalent.CAMERA_REQUEST_CODE)
                && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).fit().centerCrop().into(accImage);
        }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }


    @AfterPermissionGranted(prevalent.PERMISSION_CODE)
    private void RequestCameraAndStoragePermission(int i) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(EditProfileActivity.this, perms)) {
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