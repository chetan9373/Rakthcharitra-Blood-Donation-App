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

public class register_page1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText profession,smokedisease;
    TextView bod;
    Spinner last_bdspinner,last_platelletsspinner,professionspinner,smokespinner;
    Button next2;
    String[] yesno = {"Yes","No"};
    String []last_blooddonatedarray={"Yes","No"};
    String []last_platelletarray={"Yes","No"};
    String []profarray={"Govt Employee","Pvt Employee","Professional","Student","Businessman","Others"};
    String []smokearray={"Yes","No"};

    String bgrp,gender,lastbdstring="Yes",last_ptstring="Yes",smoketext="Yes",professionstring="Govt Employee";


    private RequestQueue mRequestQueue;
    String getdata="tt";
    TextView tv;
    private Calendar mcalendar;

    private int day,month,year,age=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page1);
        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("key1");
        // for(String s:list)
        //    Log.d("check",s);
        mRequestQueue = Volley.newRequestQueue(register_page1.this);
        bod=findViewById(R.id.dateofbirth);

        professionspinner=findViewById(R.id.professionspinner);
        // last_bd=findViewById(R.id.lastblooddonation);
        //  last_platellets=findViewById(R.id.lastplateletsdonation);
        //  smokedisease=findViewById(R.id.smokedrink);
        last_bdspinner=findViewById(R.id.bloodspinners);
        last_platelletsspinner=findViewById(R.id.plateletsspinners);
        smokespinner=findViewById(R.id.smokerspineer);
        next2=findViewById(R.id.next2);
        ArrayAdapter lastbdadapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                last_blooddonatedarray);
        ArrayAdapter lastplateletsadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,last_platelletarray);
        // set simple layout resource file
        // for each item of spinner
        ArrayAdapter professionadapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                profarray);

        ArrayAdapter smokeadapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                smokearray);

        lastbdadapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        lastplateletsadapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        professionadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        smokeadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        last_bdspinner.setAdapter(lastbdadapter);
        last_platelletsspinner.setAdapter(lastplateletsadapter);
        professionspinner.setAdapter(professionadapter);
        smokespinner.setAdapter(smokeadapter);
        last_bdspinner.setOnItemSelectedListener(this);
        smokespinner.setOnItemSelectedListener(this);
        last_platelletsspinner.setOnItemSelectedListener(this);
        bod.setOnClickListener(v->opencal());

        professionspinner.setOnItemSelectedListener(this);
//last_bd.setOnClickListener(v->opencal1());
//last_platellets.setOnClickListener(v->opencal2());
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
                //   else if(last_bd.getText().toString().equals("Yes")==false&&last_bd.getText().toString().equals("No")==false)
                //       {
                //         last_bd.setError("should be Yes or No");
                //       last_bd.requestFocus();
                // }
//else if(last_platellets.getText().toString().equals("Yes")==false&&last_platellets.getText().toString().equals("No")==false)
                //               {
                //                 last_platellets.setError("Should be Yes or No");
                //             last_platellets.requestFocus();
                //           }

//else  if(last_bd.getText().toString().equals("No")&&last_platellets.getText().toString().equals("No"))
                //               {
                //                 Toast.makeText(register_page1.this,"User should be blood or platelets donor",Toast.LENGTH_SHORT).show();

                //           }
                else{
                    list.add(professionstring);
                    list.add(bod.getText().toString());

                    //String lb=last_bd.getText().toString();

                    list.add(lastbdstring);
                    //      lb=last_platellets.getText().toString();
                    //if(lb.length()==0)
                    //  list.add("No");
                    //else
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



    private void opencal() {
        mcalendar=Calendar.getInstance();
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);
        day=mcalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener  listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month, int dayOfMonth) {
                //  myCalendar.set(Calendar.YEAR, year);
                //myCalendar.set(Calendar.MONTH, month);
                //myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //  updateDate();
                bod.setText(dayOfMonth + "/" + month + "/" + year1);
                age=  year-year1;
                Log.d("check","a->"+age);
                //bod.setText();
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
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
            sdfrmt.setLenient(false);
            /* Create Date object
             * parse the string into date
             */
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
        // clearing our cache of request queue.
        //  mRequestQueue.getCache().clear();

        // below is the url from where we will be getting
        // our response in the json format.
        String url = "http://www.postalpincode.in/api/pincode/" + pinCode;

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(register_page1.this);
        // private void updateAndroidSecurityProvider() { try { ProviderInstaller.installIfNeeded(this); } catch (Exception e) { e.getMessage(); } }
        // in below line we are creating a
        // object request using volley.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside this method we will get two methods
                // such as on response method
                // inside on response method we are extracting
                // data from the json format.
                try {
                    // we are getting data of post office
                    // in the form of JSON file.
                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");
                    if (response.getString("Status").equals("Error")) {
                        // validating if the response status is success or failure.
                        // in this method the response status is having error and
                        // we are setting text to TextView as invalid pincode.
                        Toast.makeText(register_page1.this, "Invalid pincode", Toast.LENGTH_SHORT).show();
                    } else {
                        // if the status is success we are calling this method
                        // in which we are getting data from post office object
                        // here we are calling first object of our json array.
                        JSONObject obj = postOfficeArray.getJSONObject(0);
                        // inside our json array we are getting district name,
                        // state and country from our data.
                        String district = obj.getString("District");
                        String city = obj.getString("Taluk");
                        String country = obj.getString("Country");
                        //  tv.setText(district);
                        getdata=city;
                        //// Log.d("ash",getdata);

                        // list1.add(state);
                        // after getting all data we are setting this data in
                        // our text view on below line.
                        // Toast.makeText(register_page1.this, "Details of pin code is : \n" + "District is : " + district + "\n" + "State : "
                        //       + state + "\n" + "Country : " + country, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // if we gets any error then it
                    // will be printed in log cat.
                    e.printStackTrace();
                    Toast.makeText(register_page1.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // below method is called if we get
                // any error while fetching data from API.
                // below line is use to display an error message.
                Toast.makeText(register_page1.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        //  Log.d("ash",getdata);
        queue.add(objectRequest);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner sp1=(Spinner)parent;

        // Log.d("harshal",lastbdstring);
        // Log.d("harshal",last_ptstring);

        if(sp1.getId()==R.id.bloodspinners)
        {
            lastbdstring= last_blooddonatedarray[position];
            Log.d("harshal",lastbdstring);
        }
        else if(sp1.getId()==R.id.plateletsspinners)
        {

            last_ptstring=last_platelletarray[position];
            Log.d("harshal",last_ptstring);
        }
        else if(sp1.getId()==R.id.professionspinner)
        {
            professionstring=profarray[position];
        }
        else if(sp1.getId()==R.id.smokerspineer)
        {
            smoketext=smokearray[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}