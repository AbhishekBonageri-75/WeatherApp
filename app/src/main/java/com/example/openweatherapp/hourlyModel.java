package com.example.openweatherapp;

public class hourlyModel {
    String day , time , temp , desc;
    String img;

    public hourlyModel(String day, String time, String temp, String desc , String img) {
        this.day = day;
        this.time = time;
        this.temp = temp;
        this.desc = desc;
        this.img = "_"+img;
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

    public String getImg() {
        return img;
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

    public void setImg(String img) {
        this.img = img;
    }
}
