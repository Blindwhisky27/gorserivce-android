package com.user.goservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText phoneNoTextView;
    private FirebaseAuth firebaseAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        phoneNoTextView = findViewById(R.id.phoneText);
        Button nextButton = findViewById(R.id.nextButton);
        firebaseAuth = FirebaseAuth.getInstance();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authenticate();
            }
        });
    }

    private void Authenticate() {
        String phoneNumber = phoneNoTextView.getText().toString();
        if (phoneNumber.isEmpty()) {
            phoneNoTextView.setError("Please enter a number");
        } else {
            sendOtp(phoneNumber);

        }
    }

    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {

                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(AuthenticationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                Intent intent = new Intent(getApplicationContext(), OtpActivity.class)
                                        .putExtra("PhoneNumber", phoneNumber)
                                        .putExtra("verificationCode", verificationId);
                                startActivity(intent);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}