package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
Button resetbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        auth=FirebaseAuth.getInstance();
      resetbut=findViewById(R.id.resetbtn);
        resettext=findViewById(R.id.emailforpass);
        resetbut.setOnClickListener(v->resetpass());
    }

    private void resetpass() {
        if(resettext.getText().toString().trim().length()==0)
        {
            Toast.makeText(Forgot_Password.this,"Invalid email",Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference db= (DatabaseReference) FirebaseDatabase.getInstance().getReference("Users");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean f=false;
                for(DataSnapshot d:snapshot.getChildren()) {
                    Log.d("check", d.child(Constants.operaion).getValue().toString());
                    if (d.child(Constants.operaion).getValue().toString().equals(resettext.getText().toString().trim())) {
                        f=true;
                  String s=   resettext.getText().toString().trim();

                        auth.sendPasswordResetEmail(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(Forgot_Password.this, "Done", Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(Forgot_Password.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    }

                }

                if(!f)
                    Toast.makeText(Forgot_Password.this,"Mail not exist",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Forgot_Password.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}