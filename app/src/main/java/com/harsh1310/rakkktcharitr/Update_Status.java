package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Update_Status extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText reason,datetange;
Button save;
FirebaseAuth auth;
DatabaseReference users;
stored_credentials pref;
String id,bgrp;int year,month,day;
Spinner grpspinner;
Calendar mcalendar;
int age;
    String[] courses = { "Yes","No","Maybe"};
    ArrayList<String>   ar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__status);

reason=findViewById(R.id.reason);
datetange=findViewById(R.id.daterange1);
save=findViewById(R.id.ssave);
grpspinner=findViewById(R.id.sspinners);
        grpspinner.setOnItemSelectedListener(this);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        grpspinner.setAdapter(ad);
datetange.setOnClickListener(v->opencal());
        auth=FirebaseAuth.getInstance();
pref=stored_credentials.getInstance(this);
id=pref.getuserid();
   settime();
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(bgrp.equals("No"))
        {
            if(age<0)
            {
                datetange.setError("Select proper date");
                datetange.requestFocus();
            }
            else
                updatestatusfun();
        }
        else
            updatestatusfun();
    }
});

    }

    private void opencal() {
        mcalendar=Calendar.getInstance();
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);
        day=mcalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener  listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month, int dayOfMonth) {
                datetange.setText(dayOfMonth + "/" + month + "/" + year1);
                age=  year-year1;

            }
        };
        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }


    private void updatestatusfun() {

      // 8if (yesorno.getText().toString().length() > 0) {
            ArrayList<String>list=new ArrayList<>();
            String user = auth.getUid();
            users = FirebaseDatabase.getInstance().getReference("Users");

           users.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    list.add(snapshot.child(Constants.bgrp).getValue().toString());
                    list.add(snapshot.child(Constants.pincode).getValue().toString());
                   list.add(snapshot.child(Constants.add).getValue().toString());
                    list.add(snapshot.child(Constants.name).getValue().toString());
                    list.add(snapshot.child(Constants.phone).getValue().toString());
                    list.add(snapshot.child(Constants.add).getValue().toString());
                    list.add(snapshot.child(Constants.gender).getValue().toString());
                    list.add(snapshot.child(Constants.pimg).getValue().toString());
                    list.add(snapshot.child(Constants.allergy).getValue().toString());

                    list.add(snapshot.child(Constants.disease).getValue().toString());

                    list.add(snapshot.child(Constants.operaion).getValue().toString());
                    list.add(snapshot.child(Constants.pickup).getValue().toString());
                    list.add(snapshot.child(Constants.avail).getValue().toString());
                    list.add(snapshot.child("id").getValue().toString());
                   String s= snapshot.child("dtype").getValue().toString();
                      FirebaseDatabase.getInstance().getReference().child(list.get(0)).child(list.get(1)).child(id).setValue(new donorsmodel(list.get(3),list.get(4),list.get(0),list.get(2),list.get(6), list.get(7),list.get(8),list.get(9),list.get(10),bgrp,list.get(11),list.get(1),s,list.get(13),list.get(13))).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(Update_Status.this,Login_activity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Update_Status.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
Log.d("check",error.getMessage());
                }
            });


        }

    private void settime() {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000*60*10, 1000*60*60*24,pendingIntent);
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       bgrp= courses[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        //Intent intent=new Intent(Update_Status.this,Login_activity.class);
    finish();
    }
}