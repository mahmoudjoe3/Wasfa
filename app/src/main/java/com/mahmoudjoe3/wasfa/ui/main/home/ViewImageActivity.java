package com.mahmoudjoe3.wasfa.ui.main.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mahmoudjoe3.wasfa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewImageActivity extends AppCompatActivity {

    public static final String IMG_URLS = "ViewImageActivity.IMG_URLS";
    public static final String IMG_POS = "ViewImageActivity.IMG_POS";

    @BindView(R.id.viewImage_pager)
    ViewPager viewImagePager;

    private ArrayList<String> imgUrls;
    private ViewpagerAdapter pagerAdapter;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);

        imgUrls = getIntent().getStringArrayListExtra(IMG_URLS);
        pos = getIntent().getIntExtra(IMG_POS, 0);

         pagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        for (String uri : imgUrls) {
            pagerAdapter.addFragment(ViewImageFragment.newInstance(uri));
        }

        viewImagePager.setAdapter(pagerAdapter);
        viewImagePager.setCurrentItem(pos);
        viewImagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.view_image_back, R.id.view_image_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_image_back:
                onBackPressed();
                break;
            case R.id.view_image_menu:
                openmenuSheet();
                break;
        }
    }

    private void openmenuSheet() {
        BottomSheetDialog sheetDialog=new BottomSheetDialog(this,R.style.BottomSheetDialogTheme);
        View sheetView= LayoutInflater.from(this).inflate(R.layout.view_image_bottom_sheet,
                (LinearLayout)findViewById(R.id.view_image_sheet_layout));
        sheetView.findViewById(R.id.save_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted()){
                    SaveImage(getImageBitmap());
                    sheetDialog.dismiss();
                }
            }
        });
        sheetView.findViewById(R.id.share_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
                sheetDialog.dismiss();
            }
        });

        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
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

    private void shareImage() {


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitMapToUri(getImageBitmap()));
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Wasfa:: ");

        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(System.currentTimeMillis());
        File storageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Wasfa/");
        if (!storageDir.exists())
            storageDir.mkdirs();
        String fname = "WASFA_IMG_"+ timeStamp +".jpeg";
        File file = new File (storageDir, fname);
        return file;
    }

    private void SaveImage(Bitmap finalBitmap) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = null;
        try {
            f = new File(createImageFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "photo is saved", Toast.LENGTH_SHORT).show();
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SaveImage(getImageBitmap());
                } else {
                }
                return;
            }

        }
    }

    private Bitmap getImageBitmap() {
        ImageView imageView=pagerAdapter.getItem(pos).imageView;
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

        return drawable.getBitmap();
    }

    static class ViewpagerAdapter extends FragmentPagerAdapter {
        List<ViewImageFragment> fragments;

        public ViewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            fragments = new ArrayList<>();
        }

        @NonNull
        @Override
        public ViewImageFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(ViewImageFragment fragment) {
            fragments.add(fragment);
        }
    }
}