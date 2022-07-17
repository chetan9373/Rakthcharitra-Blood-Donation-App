package com.harsh1310.rakkktcharitr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class register_page extends AppCompatActivity {
EditText phonenum,name,emailaddres,pass,confirmpass,pincode;
CircleImageView pimg;
Button next;
stored_credentials pref;
Uri uri;
Spinner genderspineer,grpspinner;
    private RequestQueue mRequestQueue;
    String getdata="tt";
    private ArrayList<grpmodel> mCountryList;
    ArrayList<gendermodel>genderlist;
    private bloddgrpadapterspinner mAdapter;
    genderadapter genderAdapter;
    String bgrp,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mRequestQueue = Volley.newRequestQueue(register_page.this);
      pimg=findViewById(R.id.profimg);
        name=findViewById(R.id.name);
        emailaddres=findViewById(R.id.emailadd);

        pincode=findViewById(R.id.pincode);
        phonenum=findViewById(R.id.phonenumber);
    genderspineer=findViewById(R.id.genderspinner);
    grpspinner=findViewById(R.id.bloodgrpspinner);
       pimg.setOnClickListener(v->setprofile());
        next=findViewById(R.id.next1);
        initList();
        genderlist=new ArrayList<>();
        genderlist.add(new gendermodel("Male"));
        genderlist.add(new gendermodel("Female"));

        mAdapter = new bloddgrpadapterspinner(this, mCountryList);
        genderAdapter=new genderadapter(this,genderlist);
        grpspinner.setAdapter(mAdapter);
        genderspineer.setAdapter(genderAdapter);
        grpspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grpmodel clickedItem = (grpmodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
                bgrp=clickedCountryName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        genderspineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gendermodel clickedItem = (gendermodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getgender();
                gender=clickedCountryName;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList<String> list = new ArrayList<>();

                if (name.getText().toString().length() == 0) {
                    name.setError(" name not entered");
                    name.requestFocus();
                }
               else if (!emailValidator(emailaddres.getText().toString())) {
                    emailaddres.setError("Email address in invalid");
                    emailaddres.requestFocus();
                }


              else if(!isValid(phonenum.getText().toString()))
                {
                    phonenum.setError("Phonenum is invalid");
                    phonenum.requestFocus();
                }

              else if(!isValidPinCode(pincode.getText().toString()))
                {
                    pincode.setError("Invalid pincode");
                    pincode.requestFocus();
                }

else if(uri==null)
                {
                    Toast.makeText(register_page.this,"Please select image",Toast.LENGTH_SHORT).show();
                }
               else{
                    getDataFromPinCode(pincode.getText().toString());

                list.add(name.getText().toString().trim());
                list.add(emailaddres.getText().toString().trim());
                list.add(phonenum.getText().toString());

                list.add(pincode.getText().toString().trim());
                list.add(uri.toString());
               list.add(bgrp);
             list.add(gender);
                Intent intent=new Intent(register_page.this,register_page1.class);
                intent.putExtra("key1",list);
                startActivity(intent);}
            }
        });

    }

    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new grpmodel("A+", R.drawable.grpimg));
        mCountryList.add(new grpmodel("A-", R.drawable.grpimg));
        mCountryList.add(new grpmodel("AB+", R.drawable.grpimg));
        mCountryList.add(new grpmodel("AB-", R.drawable.grpimg));
        mCountryList.add(new grpmodel("O+", R.drawable.grpimg));
        mCountryList.add(new grpmodel("O-", R.drawable.grpimg));
        mCountryList.add(new grpmodel("B+", R.drawable.grpimg));
        mCountryList.add(new grpmodel("B-", R.drawable.grpimg));

    }

    private void setprofile() {
        if (ActivityCompat.checkSelfPermission(register_page.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(register_page.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            try {
                startActivityForResult(intent, 101);

            } catch (ActivityNotFoundException e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public  boolean isValid(String s)
    {

        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");

        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    public boolean emailValidator(String emailToText) {

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            return  true;
        } else {
            return false;
        }
    }


    private void getDataFromPinCode(String pinCode) {
        mRequestQueue.getCache().clear();
        String url = "http://www.postalpincode.in/api/pincode/" + pinCode;

        RequestQueue queue = Volley.newRequestQueue(register_page.this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");
                    if (response.getString("Status").equals("Error")) {
                        Toast.makeText(register_page.this, "Invalid pincode", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject obj = postOfficeArray.getJSONObject(0);
                        String district = obj.getString("District");
                        String city = obj.getString("Taluk");
                        String country = obj.getString("Country");
                          getdata=city+" "+district;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(register_page.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(objectRequest);


    }
    public  boolean isValidPinCode(String pinCode)
    {

        String regex
                = "^[1-9][0-9]{5}$";



        Pattern p = Pattern.compile(regex);


        if (pinCode == null) {
            return false;
        }


        Matcher m = p.matcher(pinCode);


        return m.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK)
        {
            if (data.getData() != null) {
                uri = data.getData();
                pimg.setImageURI(uri);

            }
        }

    }
}

