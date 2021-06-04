package com.mahmoudjoe3.wasfaty.ui.main.account;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.ImageCompressor;
import com.mahmoudjoe3.wasfaty.pojo.RecipePost;
import com.mahmoudjoe3.wasfaty.pojo.UserPost;
import com.mahmoudjoe3.wasfaty.prevalent.prevalent;
import com.mahmoudjoe3.wasfaty.ui.main.MainActivity;
import com.mahmoudjoe3.wasfaty.ui.main.post.PostFragment1;
import com.mahmoudjoe3.wasfaty.ui.main.viewImage.ViewImageActivity;
import com.mahmoudjoe3.wasfaty.viewModel.AccountViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    SharedPreferences sharedPreference;
    StorageReference RecipeImageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        accountViewModel=new ViewModelProvider(this).get(AccountViewModel.class);
        /*
         ** SharedPreference Code to get the logged in user
         */
        sharedPreference= getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        gson = new Gson();
        user = gson.fromJson((sharedPreference.getString("user", null)), UserPost.class);
        init();
        RecipeImageReference = FirebaseStorage.getInstance("gs://wasfa-9891c.appspot.com/").getReference().child(prevalent.refUserImages);

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
        String tag=facebook.getText().toString();

        if(!URLUtil.isValidUrl(tag)&&!tag.isEmpty()) {
            facebook.setError("facebook link not vaild");
            return;
        }
        else lnks.add(facebook.getText().toString()+"");

        tag=instagram.getText().toString();
        if(!URLUtil.isValidUrl(tag)&&!tag.isEmpty()) {
            instagram.setError("instagram link not vaild");
            return;
        }
        else lnks.add(instagram.getText().toString()+"" );

        tag=youtube.getText().toString();
        if(!URLUtil.isValidUrl(tag)&&!tag.isEmpty()) {
            youtube.setError("youtube link not vaild");
            return;
        }
        else lnks.add(youtube.getText().toString()+"" );

        user.setLinks(lnks);

        upload(imageUri,user);


    }
    private void upload(Uri mImageUri,UserPost user) {
        // compress image
        //[1] convert uri to bitmap
        Bitmap bitmap = uriToBitmap(mImageUri);
        //[2] encode image
        String code = ImageCompressor.encode_Image_To_String(bitmap, 45);
        //[3] decode image
        Bitmap CodedBitmap = ImageCompressor.decode_String_To_Image(code);
        //[4] convert bitmap to uri
        mImageUri = bitMapToUri(CodedBitmap);
        RecipeImageReference.child(mImageUri.getLastPathSegment())
                .putFile(mImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(uri -> {
                                String downloadUri = uri.toString();
                                Log.d("TAG####", "upload: "+downloadUri);
                                user.setImageUrl(downloadUri);
                                accountViewModel.UpdateUser(user).enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        Toast.makeText(EditAccountActivity.this, "Profile edited succeccfully", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = sharedPreference.edit();
                                        editor.putString("user", new Gson().toJson(user));
                                        editor.commit();
                                        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
                                        intent.putExtra("sharedEdit", new Gson().toJson(user));
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Toast.makeText(EditAccountActivity.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditAccountActivity.this, "Uploading image error :: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("TAG####", "upload: "+e.getMessage());

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
       // EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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

    public Uri bitMapToUri(Bitmap bmp) {
        // for Image send ignore URI error
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Uri bmpUri=null;
        try {
            File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private Bitmap uriToBitmap(Uri uri) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}