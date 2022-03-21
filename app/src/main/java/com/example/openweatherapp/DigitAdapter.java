package com.example.openweatherapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DigitAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "DigitAdapter";
    private final List<hourlyModel> digits;
    private final MainActivity mainAct;

    DigitAdapter(List<hourlyModel> list, MainActivity ma) {

        this.digits = list;
        mainAct = ma;
//        Toast.makeText(mainAct, "Inside Adapter(Constructor)", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.digit_entry, parent, false);

//        Toast.makeText(mainAct, "Inside Adapter(onCreateViewHolder)", Toast.LENGTH_SHORT).show();
        itemView.setOnClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Employee " + position);

        hourlyModel model = digits.get(position);

//        holder.value.setText(digits.get(position));
//        holder.value2.setText(digits.get(position));
//        holder.value3.setText(digits.get(position));
//        holder.value4.setText(digits.get(position));
        holder.value.setText(model.getDay());
        holder.value2.setText(model.getTime());
        holder.value3.setText(model.getTemp());
        holder.value4.setText(model.getDesc());
        Toast.makeText(mainAct, "Inside Adapter(onBindViewHolder)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mainAct, "digits: size"+digits.size(), Toast.LENGTH_SHORT).show();
        return digits.size();
    }

}