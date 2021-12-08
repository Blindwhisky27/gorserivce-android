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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

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

        otpTextView = findViewById(R.id.otpText);
        resendTextView = findViewById(R.id.resendTextView);
        Button verifyButton = findViewById(R.id.verifyButton);
        firebaseAuth = FirebaseAuth.getInstance();

        PhoneNumber = getIntent().getStringExtra("PhoneNumber");

        sendOtp(PhoneNumber);
        resendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyButton.setEnabled(true);
                PhoneAuthOptions.Builder options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber("+91" + PhoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(OtpActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                    @Override
                                    public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NotNull FirebaseException e) {
                                        Toast.makeText(OtpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId,
                                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                        mResendToken = token;

                                        Toast.makeText(OtpActivity.this, "Otp resent successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpTextView.getText().toString();
                if (code.isEmpty()) {
                    otpTextView.setError("Enter a otp");

                } else {
                    verifyButton.setEnabled(false);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(OtpActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                retriveUserData();


                            } else {
                                otpTextView.setError("Wrong otp");
                                verifyButton.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
    }

    private void retriveUserData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(uid).child("username").setValue("name").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Wirte yes", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(), "Wirte no", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Intent intent;
                if (!snapshot.exists()) {
                    intent = new Intent(getApplicationContext(), AddUserActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    intent = new Intent(getApplicationContext(), NavigationActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {

                            }

                            @Override
                            public void onVerificationFailed(@NotNull FirebaseException e) {
                                Toast.makeText(OtpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                mResendToken = token;
                                verificationCode = verificationId;
                                Toast.makeText(OtpActivity.this, "Otp sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}