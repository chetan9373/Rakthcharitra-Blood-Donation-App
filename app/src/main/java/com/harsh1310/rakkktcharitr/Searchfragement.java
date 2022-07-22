package com.harsh1310.rakkktcharitr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Searchfragement extends Fragment {
    EditText searchpin;
    Spinner bgrplist;
    Button searchbtn;
    private bloddgrpadapterspinner mAdapter;
    ArrayList<grpmodel> mCountryList;
   String bgrp;
   stored_credentials pref;
    public Searchfragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_searchfragement, container, false);

        searchpin = rootview.findViewById(R.id.spincode);
         bgrplist= rootview.findViewById(R.id.sgrpspinner);
         searchbtn=rootview.findViewById(R.id.searchbtn);
         pref=stored_credentials.getInstance(getActivity());
        initlist();
        mAdapter = new bloddgrpadapterspinner(getActivity(), mCountryList);

        bgrplist.setAdapter(mAdapter);
        bgrplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grpmodel clickedItem = (grpmodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
                bgrp = clickedCountryName;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

searchbtn.setOnClickListener(v->donorsfun());

        rootview.setFocusableInTouchMode(true);
        rootview.requestFocus();

        rootview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();

                        return true;
                    }
                }
                return false;
            }
        });

return  rootview;
    }


    private void donorsfun() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(bgrp))
                {
                    if(snapshot.child(bgrp).hasChild(searchpin.getText().toString().trim()))
                    {
                       {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(bgrp);
                            list.add(searchpin.getText().toString());
                            Intent intent = new Intent(getActivity(), MainActivity2.class);
                            intent.putExtra("key5", list);
                            startActivity(intent);

                        }
                    }
                    else
                        Toast.makeText(getActivity(),"No data Found",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(),"No data Found",Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initlist() {

            mCountryList = new ArrayList<>();
            mCountryList.add(new grpmodel("A+", R.drawable.grpimg));
            mCountryList.add(new grpmodel("B+", R.drawable.grpimg));
            mCountryList.add(new grpmodel("AB+", R.drawable.grpimg));
            mCountryList.add(new grpmodel("O+", R.drawable.grpimg));
            mCountryList.add(new grpmodel("A-", R.drawable.grpimg));
            mCountryList.add(new grpmodel("B-", R.drawable.grpimg));
            mCountryList.add(new grpmodel("AB-", R.drawable.grpimg));
            mCountryList.add(new grpmodel("O-", R.drawable.grpimg));
        }


}