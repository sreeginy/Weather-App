package com.sreeginy.weather.Model;

import com.sreeginy.weather.WeatherData;

import org.json.JSONException;
import org.json.JSONObject;

public class Tomorrow {
    private String day;
    private String picPath;
    private String status;
    private int highTemp;
    private int lowTemp;

    public Tomorrow(String day, String picPath, String status, int highTemp, int lowTemp) {
        this.day = day;
        this.picPath = picPath;
        this.status = status;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public Tomorrow() {

    }

    public static Tomorrow fromJson(JSONObject jsonObject) throws JSONException {

        Tomorrow tomorrow = new Tomorrow();

        tomorrow.status = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
        tomorrow.lowTemp(jsonObject.getJSONObject("main").getInt("temp_min"));
        tomorrow.highTemp(jsonObject.getJSONObject("main").getInt("temp_max"));



        return tomorrow;

    }



    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }
}
