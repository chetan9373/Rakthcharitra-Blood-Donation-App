package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forgot_Password extends AppCompatActivity {
    FirebaseAuth auth;
    EditText resettext;
    AppCompatButton resetbut;
    String mEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        auth = FirebaseAuth.getInstance();
        resetbut = findViewById(R.id.resetbtn);
        resettext = findViewById(R.id.emailforpass);
        resetbut.setOnClickListener(v -> resetpass());
    }

    private void resetpass() {
        mEmailId = resettext.getText().toString().trim();

        if (mEmailId.isEmpty()){
            Toast.makeText(Forgot_Password.this, "Enter email", Toast.LENGTH_SHORT).show();

        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailId).matches()){
            Toast.makeText(Forgot_Password.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
        }else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            DatabaseReference db = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Users");
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean f = false;
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Log.d("check", d.child(Constants.operaion).getValue().toString());
                        if (d.child(Constants.operaion).getValue().toString().equals(resettext.getText().toString().trim())) {
                            f = true;
                            String s = resettext.getText().toString().trim();

                            auth.sendPasswordResetEmail(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Forgot_Password.this);
                                        builder.setMessage("Email end Successfully. If not received, check your spam box. !!")
                                                .setNegativeButton("Ok", (dialog, which) -> {

                                                    startActivity(new Intent(Forgot_Password.this, Login_activity.class));
                                                    finish();
                                                    dialog.dismiss();
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();


                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Forgot_Password.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            break;
                        }
                    }

                    if (!f) {
                        progressDialog.dismiss();
                        Toast.makeText(Forgot_Password.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Forgot_Password.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}