package com.example.openweatherapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class WeekWeatherAdapter extends RecyclerView.Adapter<MyWeekViewHolder> {

    private static final String TAG = "WeekWeatherAdapter";
    private final List<Weather> weatherList;
    private final WeekWeather weekAct;

    public WeekWeatherAdapter(List<Weather> weatherList , WeekWeather wa) {
        this.weatherList = weatherList;
        this.weekAct = wa;
    }

    @NonNull
    @Override
    public MyWeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.week_row2, parent, false);

        itemView.setOnClickListener((View.OnClickListener) weekAct);
//        itemView.setOnLongClickListener((View.OnLongClickListener) weekAct);

        return new MyWeekViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWeekViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Weather " + position);

        Weather weather = weatherList.get(position);

        holder.day_date.setText(weather.getDay_date());
        holder.high_low.setText(weather.getHigh_low());
        holder.description.setText(weather.getDescription());
        holder.precip.setText(weather.getPrecip());
        holder.uvi.setText(weather.getUvi());
        holder.morn.setText(weather.getMorn());
        holder.day.setText(weather.getDay());
        holder.eve.setText(weather.getEve());
        holder.night.setText(weather.getNight());
//        holder.image_icon.setText(weather.getImage_icon());

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
