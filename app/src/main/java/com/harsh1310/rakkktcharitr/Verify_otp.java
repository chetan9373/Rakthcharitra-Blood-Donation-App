package com.harsh1310.rakkktcharitr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Verify_otp extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button verifybtn;
    EditText otptext;
    String otp;
    TextView resernotp;
    private String verificationId;
    ProgressDialog progressDialog;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        //   Intent intent = getIntent();
        list = (ArrayList<String>) getIntent().getSerializableExtra("key8");

        verifybtn = findViewById(R.id.buttonget_otp);
        otptext = findViewById(R.id.otptext);
        resernotp = findViewById(R.id.resendotp);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        resernotp.setOnClickListener(v -> resendotpfn());
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(otptext.getText().toString())) {
                    Toast.makeText(Verify_otp.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(otptext.getText().toString());
                }
            }
        });
        sendVerificationCode(list.get(13));

    }

    private void resendotpfn() {
        Toast.makeText(getApplicationContext(), "Otp Sent", Toast.LENGTH_SHORT).show();
        sendVerificationCode(list.get(13));

    }


    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();


                            Intent i = new Intent(Verify_otp.this, register_page3.class);
                            i.putExtra("key3", list);
                            startActivity(i);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Verify_otp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            Log.d("errorMessage", task.getException().getMessage());
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                otptext.setText(code);

                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verify_otp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
}
