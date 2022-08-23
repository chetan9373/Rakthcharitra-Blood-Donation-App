package com.harsh1310.rakkktcharitr;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView rview;
    FirebaseRecyclerOptions<donorsmodel> options;
    donorsadapter adapter;
    DatabaseReference dbref;
    stored_credentials pref;
    Context con;
    TextView tv;
    ArrayList<String> list;
    private ArrayList<donorsmodel> courseModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list = (ArrayList<String>) getIntent().getSerializableExtra("key5");
        //   Log.d("check",list.get(0));
        rview = findViewById(R.id.rview);
        courseModelArrayList = new ArrayList<>();
        con = this;
        DatabaseReference db = (DatabaseReference) FirebaseDatabase.getInstance().getReference(list.get(0)).child(list.get(1));
        options = new FirebaseRecyclerOptions.Builder<donorsmodel>().setQuery(db, donorsmodel.class).build();
        setuprecyclerview();
    }


    private void setuprecyclerview() {


        rview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new donorsadapter(options, getApplicationContext());
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