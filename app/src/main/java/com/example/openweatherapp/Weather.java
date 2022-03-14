package com.example.openweatherapp;

public class Weather {
    private String day;
    private String time;
//    private String icon;
    private String temperature;
    private String description;


    public Weather() { }

    public Weather(String day, String time,  String temperature, String description) {
        this.day = day;
        this.time = time;
//        this.icon = icon;
        this.temperature = temperature;
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


