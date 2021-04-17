package com.mahmoudjoe3.wasfa.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class MyLogic {

    public static boolean haveNetworkConnection(Context application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null &&
                    (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getTimeFrom(long PostTimeInMileSec){
        long postFromInMile=System.currentTimeMillis()-PostTimeInMileSec;
        System.out.println("current "+System.currentTimeMillis());
        System.out.println("given "+PostTimeInMileSec);
        int postFromInSec= (int) (postFromInMile/1000);
        String time="";
        if(postFromInSec<60){
            time="Now";
        }else if(postFromInSec<(60*60)){
            time= (postFromInSec/60) +" Min";
        }else if(postFromInSec<(60*60*24)){
            time= (postFromInSec/(60*60)) +" Hour";
        } else if(postFromInSec<(60*60*24*7)){
            time= (postFromInSec/(60*60*24)) +" Day";
        }else if(postFromInSec<(60*60*24*30)){
            time= (postFromInSec/(60*60*24*7)) +" Week";
        }else if(postFromInSec<(60*60*24*30*12)){
            time= (postFromInSec/(60*60*24*30)) +" Month";
        }else {
            time= (postFromInSec/(60*60*24*30*12)) +" Year";
        }
        return time;
    }
}
