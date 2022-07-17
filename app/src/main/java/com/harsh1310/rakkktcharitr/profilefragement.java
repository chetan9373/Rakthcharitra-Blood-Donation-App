package com.harsh1310.rakkktcharitr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class profilefragement extends Fragment {
stored_credentials pref;
    private GoogleApiClient mGoogleApiClient;

TextView usergender,userbloodgrp,userloc,userpickup,useravailibility,usercontact,dtype;
CircleImageView pimg;
Button sendmsg;
TextView name,prof;
ImageButton logout;
    public profilefragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
pref=stored_credentials.getInstance(getActivity());
View rootview=inflater.inflate(R.layout.fragment_profilefragement, container, false);
logout=rootview.findViewById(R.id.logoutbtn);
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Toast.makeText(getActivity(),"Click",Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(),Login_activity.class));
        pref.checkforlogin("0");
        getActivity().finish();
    }
});
name=rootview.findViewById(R.id.dname);
userbloodgrp=rootview.findViewById(R.id.dtype);
usergender=rootview.findViewById(R.id.dpgender);
userloc=rootview.findViewById(R.id.fLocation1);
useravailibility=rootview.findViewById(R.id.favailable);
userpickup=rootview.findViewById(R.id.fdrop1);
pimg=rootview.findViewById(R.id.dpimg);
usercontact=rootview.findViewById(R.id.dcontact1);
String uid= pref.getuserid();

DatabaseReference db= FirebaseDatabase.getInstance().getReference("Users");
        db.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


         String dtpe=  snapshot.child(Constants.dtype).getValue().toString();
           userbloodgrp.setText(dtpe);
           String gender=snapshot.child(Constants.gender).getValue().toString();
           usergender.setText(gender);
           String loc=snapshot.child(Constants.add).getValue().toString();
           userloc.setText(loc);
           String avail=snapshot.child(Constants.avail).getValue().toString();
           useravailibility.setText(avail);
           String pickup=snapshot.child(Constants.pickup).getValue().toString();
           userpickup.setText(pickup);
           usercontact.setText(snapshot.child(Constants.phone).getValue().toString());
           String img=snapshot.child(Constants.pimg).getValue().toString();
//pimg.setImageURI(Uri.parse(img));

                Glide.with(getActivity()).load(img).into(pimg);
           name.setText(snapshot.child(Constants.name).getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootview;
    }

    private void signout() {

    }
}