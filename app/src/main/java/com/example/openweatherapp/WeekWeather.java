package com.example.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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

        recyclerView = findViewById(R.id.week_recycler);
        recyclerView.setAdapter(wAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        textView = findViewById(R.id.tv);
//        textView.setMovementMethod(new ScrollingMovementMethod());

        //get the data from the main activity using intent;
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String t_response = intent.getStringExtra("api");
//        textView.setText(t_response);
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

        //getting daily objects from daily array
//        for (int i=0;i<dailyArray.length();i++){
//            try {
//                dailyObject = dailyArray.getJSONObject(i);
////                textView.setText("--\n"+i+dailyArray.get(i).toString());
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }



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
                precip = dailyObject.getString("pop");
                uvi = dailyObject.getString("uvi");

                high_low = dailyTemp.getString("max")+"/"+dailyTemp.getString("min");
                morn = dailyTemp.getString("morn");
                day = dailyTemp.getString("day");
                eve = dailyTemp.getString("eve");
                night = dailyTemp.getString("night");

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
        int pos = recyclerView.getChildLayoutPosition(view);
        Weather w = weatherList.get(pos);
        Toast.makeText(this, "You clicked:- "+w.toString(), Toast.LENGTH_SHORT).show();

    }
}//WeekWeather (class);