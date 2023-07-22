package com.example.vibe.Otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.vibe.R;
import com.example.vibe.SignupActivity;
import com.example.vibe.databinding.ActivityOtpVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerifyActivity extends AppCompatActivity {

    private ActivityOtpVerifyBinding binding;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextInput();

        binding.textMobile.setText(String.format(
                "+91-%s",getIntent().getStringExtra("phone")
        ));

        verificationId = getIntent().getStringExtra("verificationId");

        binding.textResendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OtpVerifyActivity.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBarVerify.setVisibility(View.VISIBLE);
                binding.buttonVerify.setVisibility(View.INVISIBLE);
                if (binding.inputout1.getText().toString().trim().isEmpty() ||
                        binding.inputout2.getText().toString().trim().isEmpty() ||
                        binding.inputout3.getText().toString().trim().isEmpty() ||
                        binding.inputout4.getText().toString().trim().isEmpty() ||
                        binding.inputout5.getText().toString().trim().isEmpty() ||
                        binding.inputout6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid", Toast.LENGTH_SHORT).show();
                } else {
                    if (verificationId != null) {
                        String code = binding.inputout1.getText().toString().trim() +
                                binding.inputout2.getText().toString().trim() +
                                binding.inputout3.getText().toString().trim() +
                                binding.inputout4.getText().toString().trim() +
                                binding.inputout5.getText().toString().trim() +
                                binding.inputout6.getText().toString().trim();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth
                                .getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    binding.progressBarVerify.setVisibility(View.VISIBLE);
                                    binding.buttonVerify.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(OtpVerifyActivity.this, SignupActivity.class);
                                    intent.putExtra("phone",binding.textMobile.getText().toString().trim());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else {
                                    binding.progressBarVerify.setVisibility(View.GONE);
                                    binding.buttonVerify.setVisibility(View.VISIBLE);
                                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void editTextInput() {
        binding.inputout1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputout2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputout2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputout3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputout3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputout4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputout4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputout5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.inputout5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputout6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}