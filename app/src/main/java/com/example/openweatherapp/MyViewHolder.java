package com.example.openweatherapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView value;

    MyViewHolder(View view) {
        super(view);
        value = view.findViewById(R.id.value);
    }

}
