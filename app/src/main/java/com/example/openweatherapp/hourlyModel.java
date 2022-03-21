package com.example.openweatherapp;

public class hourlyModel {
    String day , time , temp , desc;

    public hourlyModel(String day, String time, String temp, String desc) {
        this.day = day;
        this.time = time;
        this.temp = temp;
        this.desc = desc;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
