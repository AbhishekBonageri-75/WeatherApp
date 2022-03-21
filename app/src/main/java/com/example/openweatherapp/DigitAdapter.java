package com.example.openweatherapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DigitAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "DigitAdapter";
    private final List<String> digits;
    private final MainActivity mainAct;

    DigitAdapter(List<String> list, MainActivity ma) {
        this.digits = list;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.digit_entry, parent, false);

        itemView.setOnClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Employee " + position);

        holder.value.setText(digits.get(position));
    }

    @Override
    public int getItemCount() {
        return digits.size();
    }

}