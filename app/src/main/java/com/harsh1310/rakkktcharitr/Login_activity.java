package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class Login_activity extends AppCompatActivity {
    private static final int REQUEST_CODE = 102;
    String m, p;
    EditText loginemail, loginpass;
    AppCompatButton loginbtn;
    TextView forgotpass;
    LinearLayout donthaveacnt;
    FirebaseAuth auth;
    stored_credentials pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        pref = stored_credentials.getInstance(this);
        if (pref.getlogin().equals("1")) {
            Intent intent = new Intent(Login_activity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        loginbtn = findViewById(R.id.loginbtn);
        loginemail = findViewById(R.id.loginemail);
        loginpass = findViewById(R.id.loginpass);
        donthaveacnt = findViewById(R.id.donthaveacnt);
        forgotpass = findViewById(R.id.forgotpassword);


        auth = FirebaseAuth.getInstance();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m = loginemail.getText().toString();
                p = loginpass.getText().toString();

                if (m.isEmpty()) {
                    Toast.makeText(Login_activity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(m).matches()) {
                    Toast.makeText(Login_activity.this, "Please Enter valid Email Id", Toast.LENGTH_SHORT).show();
                } else if (p.isEmpty()) {
                    Toast.makeText(Login_activity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (p.length() < 8) {
                    Toast.makeText(Login_activity.this, "Your Enter valid Password", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }

            }
        });
        donthaveacnt.setOnClickListener(v -> gotosignup());
        forgotpass.setOnClickListener(v -> resetpass());


    }

    private void resetpass() {
        Intent intent = new Intent(Login_activity.this, Forgot_Password.class);
        startActivity(intent);
    }


    private void gotosignup() {
        Intent intent = new Intent(Login_activity.this, register_page.class);
        startActivity(intent);
    }

    private void login() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        Log.d("check", "done");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(m, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pref.checkforlogin("1");
                    pref.setid(auth.getUid());
                    progressDialog.dismiss();

                    Intent intent = new Intent(Login_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Login_activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}