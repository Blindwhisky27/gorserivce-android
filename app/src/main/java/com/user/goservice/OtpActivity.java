package com.user.goservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivity extends AppCompatActivity {
    String PhoneNumber, verificationCode;
    private EditText otpTextView;
    private TextView resendTextView;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpTextView = findViewById(R.id.phoneText);
        resendTextView = findViewById(R.id.resendTextView);
        Button verifyButton = findViewById(R.id.verifyButton);
        firebaseAuth = FirebaseAuth.getInstance();

        PhoneNumber = getIntent().getStringExtra("PhoneNumber");
        verificationCode = getIntent().getStringExtra("verificationCode");

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpTextView.getText().toString();
                if (code.isEmpty()) {
                    otpTextView.setError("Enter a otp");

                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(OtpActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                otpTextView.setError("Wrong otp");
                            }
                        }
                    });
                }
            }
        });
    }
}