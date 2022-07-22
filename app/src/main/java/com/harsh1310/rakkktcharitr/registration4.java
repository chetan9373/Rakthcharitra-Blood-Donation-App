package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class registration4 extends AppCompatActivity {
    EditText remarks,password,confirmpass;
    Button signup;
    ArrayList<String>list;
    stored_credentials pref;
    Spinner dropickupspinner;
    String pickdroptext="No";
    String []pickdroparrray={"Yes","No"};
    ArrayList<pickupmodel>pickist;
    pickadapter pkadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration4);
        list = (ArrayList<String>) getIntent().getSerializableExtra("key4");
        pref=stored_credentials.getInstance(this);

        dropickupspinner=findViewById(R.id.pickdropspineer);
        remarks=findViewById(R.id.Remarks);
        password=findViewById(R.id.password);
        confirmpass=findViewById(R.id.ConfirmPassword);
        signup=findViewById(R.id.signup);
        pickist=new ArrayList<>();
        pickist.add(new pickupmodel("Yes"));
        pickist.add(new pickupmodel("No"));
        pkadapter=new pickadapter(this,pickist);
                dropickupspinner.setAdapter(pkadapter);
        dropickupspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickupmodel clickedItem = (pickupmodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getpickup();
                pickdroptext=clickedCountryName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signup.setOnClickListener(v->createuser());
    }

    private void createuser() {

        String pass=password.getText().toString().trim();

        if(pass.length()<8)
        {Toast.makeText(registration4.this,"Password should be gretaer than 8",Toast.LENGTH_SHORT).show();
            return;}
        if(confirmpass.getText().toString().trim().equals(pass)==false)
        {
            Toast.makeText(registration4.this,"Password and Confirm password should be matched",Toast.LENGTH_SHORT).show();
            return;
        }
        String pup;
        list.add(pickdroptext);
        pup=remarks.getText().toString();
        if(pup.trim().length()==0)
            list.add("No");
        else
            list.add("Yes");
        list.add(pass);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing");
        progressDialog.show();



        FirebaseAuth auth=   FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(list.get(1),list.get(19)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    HashMap<String, String> hmap = new HashMap<>();
                    hmap.put("last_pl",list.get(10));

                    hmap.put("id", FirebaseAuth.getInstance().getUid());

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PostsImg/"+System.currentTimeMillis()+"."+getextension());
                    storageReference.putFile(Uri.parse(list.get(4))).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                String al=list.get(14).trim();
                                if(al.length()==0)
                                    al="No";
                                String ds=list.get(15).trim();
                                if(ds.length()==0)
                                    ds="No";
                                String op=list.get(16).trim();
                                if(op.length()==0)
                                    op="No";
                                String pickup=list.get(17).trim();
                                if(pickup.length()==0)
                                    pickup="No";
                                String s;
                                if(list.get(11).equals("No"))
                                    s="Blood Donor";
                                else
                                    s="Blood Donor & Plasma Donor";
                                DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users").child(hmap.get("id"));
                                users.setValue(new usermodel(list.get(0),list.get(2),list.get(5),list.get(11),list.get(6), list.get(4),al,ds,list.get(1),"No",pickup,list.get(3),s,hmap.get("id"),list.get(7))).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {


                                            Toast.makeText(registration4.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(registration4.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(registration4.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    String al=list.get(13).trim();
                    if(al.length()==0)
                        al="No";
                    String ds=list.get(14).trim();
                    if(ds.length()==0)
                        ds="No";
                    String op=list.get(15).trim();
                    if(op.length()==0)
                        op="No";
                    String pickup=list.get(16).trim();
                    if(pickup.length()==0)
                        pickup="No";

                    String n="No";
                    if(hmap.get("last_pl")!="No") {

                        DatabaseReference plamsadonors = FirebaseDatabase.getInstance().getReference("Plsma").child(hmap.get("id"));
                        plamsadonors.setValue(new plasmamodel(list.get(0),list.get(2),list.get(5),list.get(11),list.get(6),list.get(5),al,ds,op,"No",pickup,list.get(3))).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                    DatabaseReference donors=FirebaseDatabase.getInstance().getReference(list.get(5)).child(list.get(3)).child(hmap.get("id"));
                    String s;
                    if(list.get(11).equals("No"))
                        s="Blood Donor";
                    else
                        s="Blood Donor & Plasma Donor";

                    donors.setValue(new donorsmodel(list.get(0),list.get(2),list.get(5),list.get(11),list.get(6), list.get(4),al,ds,op,"No",pickup,list.get(3),s,hmap.get("id"),list.get(7))).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                donorsmodel db1=new donorsmodel();
                                progressDialog.dismiss();
                                pref.setid(hmap.get("id"));
                                pref.checkupdate("1");
                                Toast.makeText(registration4.this, "You have registered successfullly", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(registration4.this, Update_Status.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(registration4.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(registration4.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }




        });

    }



    private String getextension() {
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(Uri.parse(list.get(6))));
    }

}