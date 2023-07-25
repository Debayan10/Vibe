package com.example.vibe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.vibe.Otpverification.OtpSendActivity;
import com.example.vibe.databinding.ActivitySigninBinding;
import com.example.vibe.utilities.Constants;
import com.example.vibe.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.textSignUp.setOnClickListener( v ->
                startActivity(new Intent(getApplicationContext(), OtpSendActivity.class)));
        binding.buttonSignIn.setOnClickListener(v ->{
            if(isValidSignInDetails()) {
                signIn();
            }
        });

    }

    private void signIn() {
        loading(true);
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_PHONE, binding.inputPhno.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.inputPass.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task. isSuccessful() && task.getResult() != null
                        && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot =task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity( intent);
                        loading( false);
                    } else {
                        loading(false);
                        showToast( "Unable to sign in");
                    }
                });
    }




    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.aniProgressBar.setVisibility(View.VISIBLE);
        } else {
            binding.aniProgressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignIn.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails() {
        if (binding.inputPhno.getText().toString().trim().isEmpty()) {
            showToast("Enter Phone Number");
            return false;
//        } else if (!Patterns.PHONEmatcher(binding.inputPhno.getText().toString().matches())) {
//            showToast("Enter valid phone number");
//            return false;
        } else if (binding.inputPass.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else {
            return true;
        }

    }


}