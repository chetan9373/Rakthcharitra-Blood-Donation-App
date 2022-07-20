package com.harsh1310.rakkktcharitr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Ourteamfragement extends Fragment {
ImageButton acharyabtn,atuldevbtn,prashantbtn,radhikabtn,chetanbtn,harshalbtn;
    public Ourteamfragement() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_ourteamfragement, container, false);
        acharyabtn=rootview.findViewById(R.id.acharyalnk);
        atuldevbtn=rootview.findViewById(R.id.atuldevlnk);
        prashantbtn=rootview.findViewById(R.id.prashantlnk);
        radhikabtn=rootview.findViewById(R.id.radhikslnk);
        chetanbtn=rootview.findViewById(R.id.chetanlnk);
        harshalbtn=rootview.findViewById(R.id.harshallnk);
        //acharyabtn.setOnClickListener(v->openacharyaln());
        atuldevbtn.setOnClickListener(v->openatullnk());
        prashantbtn.setOnClickListener(v->openprashantlnk());
        radhikabtn.setOnClickListener(v->openradhikalnk());
        chetanbtn.setOnClickListener(v->openchetnlnk());
        harshalbtn.setOnClickListener(v->openhasrahlnk());
        return rootview;
    }

    private void openprashantlnk() {
        String theurl = "https://www.linkedin.com/in/vhatkarprash";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openchetnlnk() {
        String theurl = "https://www.linkedin.com/in/chetan-harawat-499a011a2/";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openradhikalnk() {
        String theurl = "https://www.linkedin.com/in/radhika-sanghai-2497441a2";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openatullnk() {
        String theurl = "https://www.linkedin.com/in/atuldev-arora-541070183";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    /*private void openacharyaln() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }*/

    private void openhasrahlnk() {
        String theurl = "https://www.linkedin.com/in/harshal-gosavi-947313198";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }
}