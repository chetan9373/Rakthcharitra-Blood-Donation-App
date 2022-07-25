package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
   // private static sfinal String CHANNEL_1_--------ID ="channel1" -;
    EditText searchpin;
    private bloddgrpadapterspinner mAdapter;

    ArrayList<grpmodel>mCountryList;
Button getdonors,getplasmadonors;
Button tt;
String bgrp;
    boolean doubleBackToExitPressedOnce = false;
BottomNavigationView bottomNavigationView;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
bottomNavigationView=findViewById(R.id.navigation);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, new Searchfragement());
        transaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, new profilefragement());
                        transaction.commit();
                        break;
                    case R.id.search:
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.frame, new Searchfragement());
                        transaction1.commit();

                        break;
                    case  R.id.ourteam:
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.frame, new Ourteamfragement());
                        transaction2.commit();
                         break;
                    case R.id.faq:
                        FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.frame,new faqfrag());
                        transaction3.commit();
                }

                return true;
            }
        });

        // bottomNavigationView.setSelectedItemId(R.id.navhome12);

       // initlist();
      //  Spinner spinnerCountries = findViewById(R.id.spinner_bgrp);
      //  mAdapter = new bloddgrpadapterspinner(this, mCountryList);

      //  spinnerCountries.setAdapter(mAdapter);
       /* spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grpmodel clickedItem = (grpmodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
                bgrp=clickedCountryName;
                //  Toast.makeText(register_page1.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //settime();


    private void getplasmafun() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("Plsma"))
                {


                        Intent intent=new Intent(MainActivity.this,PlasmaActivity.class);

                        startActivity(intent);
                    }
                    else
                        Toast.makeText(MainActivity.this,"No data Found",Toast.LENGTH_SHORT).show();
                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void settime() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,0);
        Calendar c = Calendar.getInstance();
       // c.set(Calendar.HOUR_OF_DAY, 19);
       //c.set(Calendar.MINUTE, 14);
        //c.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+100*60, 1000*60,pendingIntent);
    }

    private void getdonorsfun() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
       rootRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.hasChild(bgrp))
               {//Log.d("che")
                   if(snapshot.child(bgrp).hasChild(searchpin.getText().toString()))
                   {
                       ArrayList<String>list=new ArrayList<>();
                       list.add(bgrp);
                       list.add(searchpin.getText().toString());
                       Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                       intent.putExtra("key4",list);
                       startActivity(intent);
                   }
                   else
                   Toast.makeText(MainActivity.this,"No data Found",Toast.LENGTH_SHORT).show();
               }
               else
                   Toast.makeText(MainActivity.this,"No data Found",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
           }
       });
    }
    protected  void sms()

    {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage("9767409749", null, "test message", pi,null);

        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }
    protected void sendSMSMessage() {
       // phoneNo = txtphoneNo.getText().toString();
        //message = txtMessage.getText().toString();
String phoneNo,message;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+919011527806", null, "Hi bro", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
*/
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}