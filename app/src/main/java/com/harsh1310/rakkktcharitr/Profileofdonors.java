package com.harsh1310.rakkktcharitr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profileofdonors extends AppCompatActivity {
CircleImageView cimg;
Button sendmsg;
TextView dpgender,dpcontact,dtype,dpavailability,dppickp,dplocation;
TextView name,prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileofdonors);
      ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("key6");
        cimg=findViewById(R.id.dprof1);
        name=findViewById(R.id.dname1);
      
        dpgender=findViewById(R.id.dgender1);
        dpcontact=findViewById(R.id.dcontact1);

        dtype=findViewById(R.id.dtype1);

        dpavailability=findViewById(R.id.davailable1);

        dppickp=findViewById(R.id.drop1);
        dplocation=findViewById(R.id.dLocation1);

        sendmsg=findViewById(R.id.sendmsg);
    cimg.setImageURI(Uri.parse(list.get(0)));
     name.setText(list.get(1));
     dtype.setText(list.get(3));
     dpgender.setText(list.get(4));
     dpcontact.setText(list.get(5));
     dpavailability.setText(list.get(6));
     dplocation.setText(list.get(7));
     dppickp.setText(list.get(8));
sendmsg.setOnClickListener(v->sendmsgfn());
    }

    private void sendmsgfn() {

       Uri uri = Uri.parse("smsto:" + dpcontact.getText().toString());
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));

    }

    }
