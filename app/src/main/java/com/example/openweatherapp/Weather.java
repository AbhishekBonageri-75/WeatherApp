package com.example.openweatherapp;

import android.widget.Toast;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Weather  {
    private String day_date , high_low, description , precip , uvi;
    private String image_icon;
    private String morn , day , eve, night;


    public Weather(String day_date, String high_low, String description, String precip, String uvi, String morn, String day, String eve, String night , String image_icon) {

        this.day_date = day_date;
        this.high_low = high_low;
        this.description = description;
        this.precip = precip;
        this.uvi = uvi;
        this.image_icon = "_"+image_icon;
        this.morn = morn;
        this.day = day;
        this.eve = eve;
        this.night = night;
    }

    public String getDay_date() {
        return day_date;
    }

    public String getHigh_low() {
        return high_low;
    }

    public String getDescription() {
        return description;
    }

    public String getPrecip() {
        return precip;
    }

    public String getUvi() {
        return uvi;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public String getMorn() {
        return morn;
    }

    public String getDay() {
        return day;
    }

    public String getEve() {
        return eve;
    }

    public String getNight() {
        return night;
    }

    public void setDay_date(String day_date) {
        this.day_date = day_date;
    }

    public void setHigh_low(String high_low) {
        this.high_low = high_low;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public void setMorn(String morn) {
        this.morn = morn;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public void setNight(String night) {
        this.night = night;
    }

}

