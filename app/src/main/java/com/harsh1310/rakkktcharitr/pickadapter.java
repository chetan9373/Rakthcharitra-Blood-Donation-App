package com.harsh1310.rakkktcharitr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class pickadapter
        extends ArrayAdapter<pickupmodel> {


    public pickadapter(@NonNull Context context, ArrayList<pickupmodel> countryList) {
        super(context, 0,countryList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.pickuplist, parent, false
            );
        }


        TextView textViewName = convertView.findViewById(R.id.pickuptext);

        pickupmodel currentItem = getItem(position);

        if (currentItem != null) {

            textViewName.setText(currentItem.getpickup());
        }

        return convertView;
    }

}
