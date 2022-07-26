package com.harsh1310.rakkktcharitr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register_page1 extends AppCompatActivity //implements AdapterView.OnItemSelectedListener {
{EditText profession,smokedisease;
    TextView bod,lastblooddonate;
    Spinner last_bdspinner,last_platelletsspinner,professionspinner,smokespinner;
    Button next2;

    String []profarray={"Govt Employee","Pvt Employee","Professional","Student","Businessman","Others"};

    ArrayList<isbloodmodel>isbloodlist;
    ArrayList<isplasmamodel>isplasmalist;
    ArrayList<professionmodel>proflist;
    ArrayList<smokemodel>smokelist;
    String bgrp,gender,lastbdstring="Yes",last_ptstring="Yes",smoketext="Yes",professionstring="Govt Employee",lastblooddonaedate="No";
    isbdadapter bdadapter;
    isplasmaadapter ptadapter;
    smokeadapter smokeadapter;
    profadapter padapter;
    stored_credentials pref;
    private RequestQueue mRequestQueue;
    String getdata="tt";
    TextView tv;
    private Calendar mcalendar,mcalendar1;

    private int day,month,year,age=100,day1,month1,year1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page1);
        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("key1");
        mRequestQueue = Volley.newRequestQueue(register_page1.this);
        bod=findViewById(R.id.dateofbirth);
        //bod.setEnabled(false);

        professionspinner=findViewById(R.id.professionspinner);
        last_bdspinner=findViewById(R.id.bloodspinners);
        last_platelletsspinner=findViewById(R.id.plateletsspinners);
        smokespinner=findViewById(R.id.smokerspineer);
        next2=findViewById(R.id.next2);
        lastblooddonate=findViewById(R.id.lastblodddonatedate);
        //lastblooddonate.setEnabled(false);
        pref=stored_credentials.getInstance(this);
         //bod.setEnabled(false);
        //lastblooddonate.setEnabled(false);
        initprof();
        isbloodlist=new ArrayList<>();
        isbloodlist.add(new isbloodmodel("Yes"));
        isbloodlist.add(new isbloodmodel("No"));
        isplasmalist=new ArrayList<>();
        isplasmalist.add(new isplasmamodel("Yes"));
        isplasmalist.add(new isplasmamodel("No"));
        smokelist=new ArrayList<>();
        smokelist.add(new smokemodel("Yes"));
        smokelist.add(new smokemodel("No"));
        bdadapter=new isbdadapter(this,isbloodlist);
        ptadapter=new isplasmaadapter(this,isplasmalist);
        padapter=new profadapter(this,proflist);
        smokeadapter=new smokeadapter(this,smokelist);
        last_bdspinner.setAdapter(bdadapter);
        last_platelletsspinner.setAdapter(ptadapter);
        smokespinner.setAdapter(smokeadapter);
        professionspinner.setAdapter(padapter);
        professionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                professionmodel clickedItem = (professionmodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getprofession();
                professionstring=clickedCountryName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        last_bdspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isbloodmodel clickedItem = (isbloodmodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getblood();
                lastbdstring=clickedCountryName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        last_platelletsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isplasmamodel m=(isplasmamodel)parent.getItemAtPosition(position);
                String mm=m.getplasma();
                last_ptstring=mm;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        smokespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                smokemodel clickedItem = (smokemodel) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getSmoke();
                smoketext=clickedCountryName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bod.setOnClickListener(v->opencal());
        lastblooddonate.setOnClickListener(v->opencal1());
        getDataFromPinCode(list.get(3));
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(age<18||age>60)
                {
                    Toast.makeText(register_page1.this,"Age should between 18 and 60",Toast.LENGTH_SHORT).show();

                }
                else if(lastbdstring.equals("No")&&last_ptstring.equals("No"))
                {
                    Toast.makeText(register_page1.this,"User should be blood or platelets donor",Toast.LENGTH_SHORT).show();
                }
                else{
                    list.add(professionstring);
                    list.add(bod.getText().toString());


                    list.add(lastbdstring);
                    pref.setlastdate(lastblooddonate.getText().toString());
                    list.add(last_ptstring);

                    Log.d("check",getdata);

                    list.add(getdata);


                    list.add(smoketext);



                    Intent intent=new Intent(register_page1.this,Otp_activity.class);
                    intent.putExtra("key2",list);
                    startActivity(intent);}

            }
        });


    }

    private void initprof() {
        proflist=new ArrayList<>();
        for(int i=0;i<6;i++)
            proflist.add(new professionmodel(profarray[i]));
    }

    private void opencal1() {
        mcalendar1=Calendar.getInstance();
        year1=mcalendar1.get(Calendar.YEAR);
        month1=mcalendar1.get(Calendar.MONTH);
        day1=mcalendar1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener  listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month, int dayOfMonth) {
                month++;
                lastblooddonate.setText(dayOfMonth + "/" + month + "/" + year1);

            }
        };

        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();

    }


    private void opencal() {
        mcalendar=Calendar.getInstance();
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);
        day=mcalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener  listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month, int dayOfMonth) {
                month++;
                bod.setText(dayOfMonth + "/" + month + "/" + year1);
                age=  year-year1;
                Log.d("check","a->"+age);
            }
        };

        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }





    public  boolean validateJavaDate(String strDate)
    {
        /* Check if date is 'null' */
        if (strDate.trim().equals(""))
        {
            return true;
        }
        /* Date is not 'null' */
        else
        {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
            sdfrmt.setLenient(false);
            try
            {
                Date javaDate = sdfrmt.parse(strDate);

            }
            /* Date format is invalid */
            catch (ParseException e)
            {
                Toast.makeText(register_page1.this,"  Invalid Date format",Toast.LENGTH_SHORT).show();
                return false;
            }
            /* Return true if date format is valid */
            return true;
        }
    }


    private void getDataFromPinCode(String pinCode) {
        mRequestQueue.getCache().clear();
        String url = "http://www.postalpincode.in/api/pincode/" + pinCode;

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(register_page1.this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");
                    if (response.getString("Status").equals("Error")) {
                        Toast.makeText(register_page1.this, "Invalid pincode", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject obj = postOfficeArray.getJSONObject(0);
                        String district = obj.getString("District");
                        String city = obj.getString("Taluk");
                        String country = obj.getString("Country");
                        getdata=city;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(register_page1.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register_page1.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(objectRequest);


    }



}