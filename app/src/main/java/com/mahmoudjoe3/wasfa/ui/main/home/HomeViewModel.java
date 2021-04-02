package com.mahmoudjoe3.wasfa.ui.main.home;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.mahmoudjoe3.wasfa.repo.FirebaseAuthRepo;

public class HomeViewModel extends ViewModel {
    private FirebaseAuthRepo authRepo;

    public HomeViewModel() {
        this.authRepo = FirebaseAuthRepo.getInstance();
    }

    public Intent getAuthIntent() {
        return authRepo.getAuthIntent();
    }

    public void authStateListener(FirebaseAuthRepo.OnAuthStateListener onAuthStateListener) {
        authRepo.authStateListener(onAuthStateListener);
    }

    public void SignOut(Context context) {
        authRepo.SignOut(context);
    }
}
