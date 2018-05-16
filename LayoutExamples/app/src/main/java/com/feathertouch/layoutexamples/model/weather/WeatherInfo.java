package com.feathertouch.layoutexamples.model.weather;

public class WeatherInfo {
    String description;
    String date;

    public WeatherInfo(String description, String date) {
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return  date + "-" + description;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
