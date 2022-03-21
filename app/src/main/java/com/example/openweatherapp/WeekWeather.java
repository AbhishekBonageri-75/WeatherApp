package com.example.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class WeekWeather extends AppCompatActivity  implements View.OnClickListener{

    private List<Weather> weatherList = new ArrayList<>();
    String icon;
    private String morn , day , eve, night;
    private String day_date , high_low, description , precip , uvi;
    TextView textView;
    RecyclerView recyclerView;
    WeekWeatherAdapter wAdapter = new WeekWeatherAdapter(weatherList, this);
//    JSONParser parser;
    JSONArray dailyArray , dailyWeather;
    JSONObject response , dailyObject , dailyTemp,dailyWeatherObj;


    private static final String TAG = "WeekWeather";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_weather);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        recyclerView = findViewById(R.id.week_recycler);
        recyclerView.setAdapter(wAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //get the data from the main activity using intent;
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String t_response = intent.getStringExtra("api");
        setTitle(title);

        //Fetching data for second activity from t_response;
        try {
            response = new JSONObject(t_response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //getting dailyArray from response object
        try {
            dailyArray = response.getJSONArray("daily");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0 ; i<=7 ; i++){
            try {
                dailyObject = dailyArray.getJSONObject(i);
                dailyTemp = dailyObject.getJSONObject("temp");
                dailyWeather = dailyObject.getJSONArray("weather");
                dailyWeatherObj = dailyWeather.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                day_date = dailyObject.getString("dt");
//                Toast.makeText(this, "day_date"+day_date, Toast.LENGTH_SHORT).show();
                day_date = convertTim(Long.parseLong(day_date)) ;
//                Toast.makeText(this, "day_date"+day_date, Toast.LENGTH_SHORT).show();
                precip = dailyObject.getString("pop");
                uvi = dailyObject.getString("uvi");

                high_low = convertTemp(dailyTemp.getString("max"))+"/"+convertTemp(dailyTemp.getString("min"));
//                high_low = convertTemp(high_low);
                morn = dailyTemp.getString("morn");
                morn = convertTemp(morn);
                day = dailyTemp.getString("day");
                day = convertTemp(day);
                eve = dailyTemp.getString("eve");
                eve = convertTemp(eve);
                night = dailyTemp.getString("night");
                night = convertTemp(night);

                description = dailyWeatherObj.getString("description");
                icon = dailyWeatherObj.getString("icon");




            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Toast.makeText(this, "_"+day_date+high_low, Toast.LENGTH_SHORT).show();
            weatherList.add(new Weather(day_date, high_low , description , precip , uvi , morn,day,eve,night,icon));
        }


    }//onCreate();


    @Override
    public void onClick(View view) {
        String s=null;
        int pos = recyclerView.getChildLayoutPosition(view);
        Weather w = weatherList.get(pos);
        try {
            dailyObject = dailyArray.getJSONObject(pos);
            s=dailyObject.getString("dt");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Long dt ;
        dt = Long.parseLong(s);
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(dt + timezone_offse(), 0, ZoneOffset.UTC);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        long date = zdt.toInstant().toEpochMilli();

        Toast.makeText(this, "Opening calendar to _ "+w.getDay_date(), Toast.LENGTH_LONG).show();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, date);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);

    }
    private long timezone_offse() {
        int l = 0;
        try {
            l = Integer.parseInt(response.getString("timezone_offset"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l;
    }

    public String convertTim(long dt){
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(dt + timezone_offset(), 0, ZoneOffset.UTC);
        String formattedTimeString="";
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("EEEE,M/d", Locale.getDefault());
        formattedTimeString = ldt.format(dtf);
        return formattedTimeString;
    }
    private long timezone_offset() {
        int l = 0;
        try {
            l = Integer.parseInt(response.getString("timezone_offset"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l;
    }
    private String convertTemp(String Skelvin ){
        float kelvin=0;
        kelvin = Float.parseFloat(Skelvin);
        int f=0;
        f= (int) ((((kelvin-273.15)*9)/5)+32);
        return String.valueOf(f);
    }
}//WeekWeather (class);