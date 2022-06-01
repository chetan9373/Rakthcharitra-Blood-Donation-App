package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class register_page3 extends AppCompatActivity //implements AdapterView.OnItemSelectedListener
 {
EditText allergy,ifallergy,ifdisease,op,ifop,drop_pick,remark;
Button next3;
    ArrayList<String>list;
    stored_credentials pref;
Spinner diseasespinner,operationspinner,allergyspinner;
String diseasetext="Yes",allergytext="Yes",optext="Yes";
String []diseasearray={"Yes","No"};
String []oparray={"Yes","No"};
String []allergyarray={"Yes","No"};
ArrayList<existingmodel>diseaseadlist;
ArrayAdapter diseaseadapter;
     ArrayList<operationmodel>operationadapterslist;
     ArrayAdapter operationadapter;
     ArrayList<allergymodel>allergymodellist;
     ArrayAdapter allergyadapter;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page3);
pref=stored_credentials.getInstance(this);
     list = (ArrayList<String>) getIntent().getSerializableExtra("key3");

     //for(String s:list)
      //  Log.d("check",s);
//Log.d("check",list.get(10));
        allergyspinner=findViewById(R.id.allergyspinners);

        operationspinner=findViewById(R.id.operationspinners);
        diseasespinner=findViewById(R.id.diseasesspinners);
     //   drop_pick=findViewById(R.id.pickup_drop);

        next3=findViewById(R.id.next3);

    setdisease();
    diseaseadapter=new existingadapter(this,diseaseadlist);
    diseasespinner.setAdapter(diseaseadapter);
    setoperation();
    operationadapter =new operationadapter(this,operationadapterslist);
    operationspinner.setAdapter(operationadapter);
    setallergy();
    allergyadapter =new allergyadapter(this,allergymodellist);
    allergyspinner.setAdapter(allergyadapter);

    //opspinner.setAdapter(opadapter);
    //allergyspinner.setAdapter(allergyadapter);
    diseasespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            existingmodel clickedItem = (existingmodel) parent.getItemAtPosition(position);
            String clickedCountryName = clickedItem.getExisting();
            diseasetext=clickedCountryName;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    operationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            operationmodel clickedItem = (operationmodel) parent.getItemAtPosition(position);
            String clickedCountryName = clickedItem.getOperation();
            optext=clickedCountryName;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    allergyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            allergymodel clickedItem = (allergymodel) parent.getItemAtPosition(position);
            String clickedCountryName = clickedItem.getAllergy();
            allergytext=clickedCountryName;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
  //  opspinner.setOnItemSelectedListener(this);
    //    allergyspinner.setOnItemSelectedListener(this);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//Toast.makeText(register_page3.this,allergytext,Toast.LENGTH_SHORT).show();
                    list.add(allergytext);
                    list.add(diseasetext);
                    list.add(optext);

                    // String allergyy=allergy.getText().toString().trim();
                    // if(allergyy.length()==0)
                    //   list.add("No");
                    // else
                    //   list.add(allergyy);
                    // String diseases=ifdisease.getText().toString().trim();
                    // if(diseases.length()==0)
                    //   list.add("No");
                    // else
                    //   list.add(diseases);
                    // String opp=op.getText().toString().trim();
                    // if(opp.length()==0)
                    //   list.add("No");
                    // else
                    //   list.add(opp);
                    Intent intent = new Intent(register_page3.this, registration4.class);
                    intent.putExtra("key4", list);
                    startActivity(intent);
                }
               //createuser();

        });
    }

    private String getextension() {
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(Uri.parse(list.get(6))));
    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner sp=(Spinner)parent;
        if(sp.getId()==R.id.diseasesspinners)
        {
            diseasetext=diseasearray[position];
        }
        else if(sp.getId()==R.id.operationspinners)
        {
            optext=oparray[position];
        }
        else if(sp.getId()==R.id.allergyspinners)
        {
            allergytext=allergyarray[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

     public void setdisease()
     {
         diseaseadlist = new ArrayList<>();
         diseaseadlist.add(new existingmodel("Yes"));
         diseaseadlist.add(new existingmodel("No"));
     }
     public void setoperation()
     {
         diseaseadlist = new ArrayList<>();
         diseaseadlist.add(new existingmodel("Yes"));
         diseaseadlist.add(new existingmodel("No"));
     }
     public void setallergy()
     {
         diseaseadlist = new ArrayList<>();
         diseaseadlist.add(new existingmodel("Yes"));
         diseaseadlist.add(new existingmodel("No"));
     }

 }

