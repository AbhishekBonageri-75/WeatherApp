package com.example.openweatherapp;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView value,value2,value3,value4;

    MyViewHolder(View view) {
        super(view);
        value = view.findViewById(R.id.value);
        value2 = view.findViewById(R.id.value2);
        value3 = view.findViewById(R.id.value3);
        value4 = view.findViewById(R.id.value4);
    }
}
