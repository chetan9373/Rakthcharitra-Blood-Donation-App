package com.harsh1310.rakkktcharitr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Otp_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtPhone, edtOTP;
    stored_credentials pref;
    private Button verifyOTPBtn, generateOTPBtn;
    ProgressDialog progressDialog;
    private String verificationId;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);
        mAuth = FirebaseAuth.getInstance();
        pref = stored_credentials.getInstance(this);
        list = (ArrayList<String>) getIntent().getSerializableExtra("key2");
        edtPhone = findViewById(R.id.input_mobile_number);

        generateOTPBtn = findViewById(R.id.buttonget_otp);

        generateOTPBtn.setOnClickListener(v -> {
            String phonenum = edtPhone.getText().toString();

            if (TextUtils.isEmpty(phonenum) && isValid(phonenum)) {
                Toast.makeText(Otp_activity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                String phone = "+91" + edtPhone.getText().toString();
                Intent intent = new Intent(Otp_activity.this, Verify_otp.class);
                list.add(phone);
                intent.putExtra("key8", list);
                startActivity(intent);

            }
        });
    }

    public boolean isValid(String s) {

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }


}