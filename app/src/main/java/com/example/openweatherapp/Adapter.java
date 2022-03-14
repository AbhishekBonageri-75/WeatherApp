package com.example.openweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Weather> weatherList;


    public Adapter(Context ctx, List<Weather> weatherList) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.weather_recycller_1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(weatherList.get(position).getDay());
        holder.time.setText(weatherList.get(position).getTime());
        holder.description.setText(weatherList.get(position).getDescription());
        holder.temperature.setText(weatherList.get(position).getTemperature());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{

        TextView day , time , temperature , description;
//        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.row_day_name);
            time = itemView.findViewById(R.id.row_time);
            temperature = itemView.findViewById(R.id.row_temperature);
            description = itemView.findViewById(R.id.row_description);
//            icon = itemView.findViewById(R.id.row_icon);
        }
    }
}
