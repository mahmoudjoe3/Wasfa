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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        Viewphoto.setVisibility(View.VISIBLE);
        Picasso.get().load(imageUri).fit().centerInside().into(Viewphoto);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, prevalent.GALLERY_REQUEST_CODE);
    }

    private void openCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("-mm-ss");

        String newPicFile = df.format(date) + ".jpg";
        String outPath = "/sdcard/" + newPicFile;
        File outFile = new File(outPath);

        mCameraFileName = outFile.toString();
        Uri outuri = Uri.fromFile(outFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        startActivityForResult(intent, prevalent.CAMERA_REQUEST_CODE);
    }
    String mCameraFileName;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == prevalent.GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).fit().centerCrop().into(accImage);
        }
        else if (requestCode == prevalent.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).fit().centerCrop().into(accImage);
                accImage.setVisibility(View.VISIBLE);
            }
            if (imageUri == null && mCameraFileName != null) {
                imageUri = Uri.fromFile(new File(mCameraFileName));
                Picasso.get().load(imageUri).fit().centerCrop().into(accImage);
                accImage.setVisibility(View.VISIBLE);
            }
            File file = new File(mCameraFileName);
            if (!file.exists()) {
                file.mkdir();
            }
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