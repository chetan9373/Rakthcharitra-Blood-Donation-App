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
     Spinner diseasespinner,opspinner,allergyspinner;
     String diseasetext="Yes",allergytext="Yes",optext="Yes";
     String []diseasearray={"Yes","No"};
     String []oparray={"Yes","No"};
     String []allergyarray={"Yes","No"};
     ArrayList<diseasemodel>diseaselist;

     ArrayList<allergymodel>allegylist;
     ArrayList<opmodel>oplist;
     diseaseadapter dadapter;
     opadapter oadpter;
     allergyadapter algyadapter;
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

         opspinner=findViewById(R.id.operationspinners);
         diseasespinner=findViewById(R.id.diseasesspinners);
         //   drop_pick=findViewById(R.id.pickup_drop);

         next3=findViewById(R.id.next3);
    /*ArrayAdapter diseaseadapter
            = new ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            diseasearray);
    ArrayAdapter opadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,oparray);
    ArrayAdapter allergyadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,allergyarray);
    // set simple layout resource file
    // for each item of spinner
    diseaseadapter.setDropDownViewResource(
            android.R.layout
                    .simple_spinner_dropdown_item);
    opadapter.setDropDownViewResource(
            android.R.layout
                    .simple_spinner_dropdown_item);

    allergyadapter.setDropDownViewResource(
            android.R.layout
                    .simple_spinner_dropdown_item);

*/
         diseaselist=new ArrayList<>();
         diseaselist.add(new diseasemodel("Yes"));
         diseaselist.add(new diseasemodel("No"));
         oplist=new ArrayList<>();
         oplist.add(new opmodel("Yes"));
         oplist.add(new opmodel("No"));
         allegylist=new ArrayList<>();
         allegylist.add(new allergymodel("Yes"));
         allegylist.add(new allergymodel("No"));
         // Set the ArrayAdapter (ad) data on the
         // Spinner which binds data to spinner
         dadapter=new diseaseadapter(this,diseaselist);
         oadpter=new opadapter(this,oplist);
         algyadapter=new allergyadapter(this,allegylist);
         diseasespinner.setAdapter(dadapter);
         opspinner.setAdapter(oadpter);
         allergyspinner.setAdapter(algyadapter);
         diseasespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 diseasemodel clickedItem = (diseasemodel) parent.getItemAtPosition(position);
                 String clickedCountryName = clickedItem.getDisease();
                 diseasetext=clickedCountryName;
                 //  Toast.makeText(register_page1.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
         opspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 opmodel clickedItem = (opmodel) parent.getItemAtPosition(position);
                 String clickedCountryName = clickedItem.getOperation();
                 optext=clickedCountryName;
                 //  Toast.makeText(register_page1.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
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
                 //  Toast.makeText(register_page1.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         // diseasespinner.setOnItemSelectedListener(this);
         // opspinner.setOnItemSelectedListener(this);
         //   allergyspinner.setOnItemSelectedListener(this);
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

    /*@Override
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
 }