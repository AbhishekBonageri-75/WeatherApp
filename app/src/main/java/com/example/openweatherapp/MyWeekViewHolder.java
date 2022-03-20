package com.example.openweatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyWeekViewHolder extends RecyclerView.ViewHolder {

    TextView day_date , high_low, description , precip , uvi ,morn , day , eve, night;
//    ImageView image_icon;


    public  MyWeekViewHolder( View itemView) {
        super(itemView);
        day_date = itemView.findViewById(R.id.week_row_day_date);

        high_low = itemView.findViewById(R.id.week_row_high_low);
        description = itemView.findViewById(R.id.week_row_description);
        precip = itemView.findViewById(R.id.week_ror_pop);
        uvi = itemView.findViewById(R.id.week_row_uvi);
        morn = itemView.findViewById(R.id.week_row_temp_morn);
        day = itemView.findViewById(R.id.week_row_temp_day);
        eve = itemView.findViewById(R.id.week_row_temp_eve);
        night = itemView.findViewById(R.id.week_row_temp_night);
//        image_icon = itemView.findViewById(R.id.week_row_icon);
    }
}
