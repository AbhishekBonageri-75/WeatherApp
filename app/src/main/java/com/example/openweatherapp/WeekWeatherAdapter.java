package com.example.openweatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class WeekWeatherAdapter extends RecyclerView.Adapter<MyWeekViewHolder> {

    private static final String TAG = "WeekWeatherAdapter";
//    MainActivity ma = new MainActivity();
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
        Toast.makeText(weekAct, "Inside Adapter(onCreateViewHolder)", Toast.LENGTH_SHORT).show();
        itemView.setOnClickListener( weekAct);
//        itemView.setOnLongClickListener((View.OnLongClickListener) weekAct);


        return new MyWeekViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWeekViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Weather " + position);


        Weather weather = weatherList.get(position);
        //Convert day_date to Day,Month/date;
//        String day = weather.getDay_date();
//        day = ma.convertTime(Long.parseLong(day),2);
        holder.day_date.setText(weather.getDay_date());
        holder.high_low.setText(weather.getHigh_low());
        holder.description.setText(weather.getDescription());
        holder.precip.setText(weather.getPrecip());
        holder.uvi.setText(weather.getUvi());
        holder.morn.setText(weather.getMorn());
        holder.day.setText(weather.getDay());
        holder.eve.setText(weather.getEve());
        holder.night.setText(weather.getNight());
        String img = weather.getImage_icon();
        Context context = holder.image_icon.getContext();
        int id = context.getResources().getIdentifier(img , "drawable" , context.getOpPackageName());
        holder.image_icon.setImageResource(id);
//        Toast.makeText(this, "Inside Adapter(onBindViewHolder)", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Inside Adapter(onBindViewHolder)", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        Toast.makeText(weekAct, "digits: size"+weatherList.size(), Toast.LENGTH_SHORT).show();
        return weatherList.size();
    }
}
