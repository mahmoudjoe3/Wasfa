package com.mahmoudjoe3.wasfa.repo;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.mahmoudjoe3.wasfa.R;

import java.util.Arrays;

public class FirebaseAuthRepo {

    private static FirebaseAuthRepo authRepo;

    static FirebaseAuth mFirebaseAuth;
    public static synchronized FirebaseAuthRepo getInstance() {
        if (authRepo == null) {
            authRepo = new FirebaseAuthRepo();
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return authRepo;
    }

    public Intent getAuthIntent() {
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                //.setLogo(R.drawable.logo)
                //.setTheme(R.style.loginStyle)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build()
                ))
                .build();
    }
    public void SignOut(Context context){
        AuthUI.getInstance().signOut(context);
    }

    public void authStateListener(OnAuthStateListener onAuthStateListener) {
        mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                mOnAuthStateListener = onAuthStateListener;
                if (user != null) {
                    mOnAuthStateListener.onAuthSuccess(user);
                } else {
                    mOnAuthStateListener.onAuthFailed();
                }
            }
        });
    }


    OnAuthStateListener mOnAuthStateListener;
    public interface OnAuthStateListener {
        void onAuthSuccess(FirebaseUser user);
        void onAuthFailed();
    }
}
