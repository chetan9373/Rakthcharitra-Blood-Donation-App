package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlasmaActivity extends AppCompatActivity {
    RecyclerView rview;
    // FirebaseRecyclerOptions<donorsmodel> options;
    plasmaadapter adapter;
    DatabaseReference dbref;
    stored_credentials pref;
    FirebaseRecyclerOptions<plasmamodel> options;
    private ArrayList<plasmamodel> courseModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plasma);

        pref = stored_credentials.getInstance(this);
        rview = findViewById(R.id.rview1);
        courseModelArrayList = new ArrayList<>();
        String uid = pref.getuserid();
        DatabaseReference db = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Plsma");
        options = new FirebaseRecyclerOptions.Builder<plasmamodel>().setQuery(db, plasmamodel.class).build();
        setuprecyclerview();
    }
/*        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("check",snapshot.getKey());
                for(DataSnapshot d:snapshot.getChildren())
                {

                    Log.d("check",d.child("name").getValue().toString());
                    String name=d.child(Constants.name).getValue().toString();
                    String phonenum=d.child(Constants.phone).getValue().toString();
                    String city=d.child(Constants.add).getValue().toString();
                    String gender=d.child(Constants.gender).getValue().toString();
                    String avail=d.child(Constants.avail).getValue().toString();

                    String allergy=d.child(Constants.allergy).getValue().toString();
                    String disease=d.child(Constants.disease).getValue().toString();
                    String op=d.child(Constants.operaion).getValue().toString();
                    String pickup=d.child("piick").getValue().toString();
       Log.d("check",name);
        Log.d("check",phonenum);
        Log.d("check",city);
        Log.d("check",gender);
        Log.d("check",avail);
        Log.d("check",allergy);
        Log.d("check",disease);
        Log.d("check",op);
               //     String name, String phonenum, String availability, String city, String gender,  String allergy, String disease, String op
                    courseModelArrayList.add(new plasmamodel(name, phonenum,avail,city,gender,allergy,disease,op,pickup));
                }

        /*for(DataSnapshot d:snapshot.getChildren())
        {
           // if(snapshot.exists())
            {
              Log.d("check",  .getKey());
                // String s= snapshot.child("name").getKey();
                String name= d.child("name").getValue().toString();
                String phonenum=d.child("phonenum").getValue().toString();
                //  String ss=snapshot.child("phonenum").getKey();
                Log.d("check",name+" "+phonenum);
                courseModelArrayList.add(new donorsmodel(name, phonenum, "dsf","sdfs"));
            }
        }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        plasmaadapter courseAdapter = new plasmaadapter(this, courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        rview.setLayoutManager(linearLayoutManager);
        rview.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new plasmaadapter.OnItemClickListener() {
            @Override
            public void onclick(int pos) {
                Intent intent=new Intent(PlasmaActivity.this,Profileofplamsa.class);
                ArrayList<String>list=new ArrayList<>();
                String dg=courseModelArrayList.get(pos).gender;
                list.add(dg);
                String dc=courseModelArrayList.get(pos).phonenum;
                list.add(dc);
                String dav=courseModelArrayList.get(pos).availability;
                list.add(dav);
                String dloc=courseModelArrayList.get(pos).city;
                list.add(dloc);
                String dpickup=courseModelArrayList.get(pos).pickup;
                list.add(dpickup);
                intent.putExtra("key5",list);
                startActivity(intent);
            }

            @Override
            public void delete(int pos) {

            }
        });
    }

*/
    private void setuprecyclerview() {


        rview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new plasmaadapter(options,getApplicationContext());
        rview.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    }
