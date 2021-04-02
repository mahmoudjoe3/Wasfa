package com.mahmoudjoe3.wasfa.ui.main.home;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.mahmoudjoe3.wasfa.R;

import com.mahmoudjoe3.wasfa.prevalent.prevalent;
import com.mahmoudjoe3.wasfa.repo.FirebaseAuthRepo;
import com.mahmoudjoe3.wasfa.ui.main.SharedViewModel;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {
    private static final String TAG = "SVM";
    HomeViewModel viewModel;
    SharedViewModel sharedViewModel;
    private int REC_AUTH_CODE=1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        EditText text=view.findViewById(R.id.edit1);
        Button button=view.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.SignOut(getActivity());
                //sharedViewModel.setData(text.getText().toString());
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.authStateListener(new FirebaseAuthRepo.OnAuthStateListener() {
            @Override
            public void onAuthSuccess(FirebaseUser user) {
//                Toast.makeText(getActivity(), "SignIn successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthFailed() {
                startActivityForResult(viewModel.getAuthIntent(),REC_AUTH_CODE);
            }
        });

        sharedViewModel=new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //recived data
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REC_AUTH_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "Signed In", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Signed In Canceled", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}