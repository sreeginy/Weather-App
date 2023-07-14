package com.sreeginy.weather.Model;


import org.json.JSONException;
import org.json.JSONObject;

public class ForecastWeatherData {
    private String day;
    private String icon;
    private String weatherType;
    private double highTemp;
    private double lowTemp;

   private String date;

    public ForecastWeatherData(String day, String icon, String weatherType, double highTemp, double lowTemp) {
        this.day = day;
        this.icon = icon;
        this.weatherType = weatherType;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }


        public ForecastWeatherData(String date, double temperature, String weatherType) {
            this.date = date;
            this.highTemp = temperature;
            this.weatherType = weatherType;
        }



    public static ForecastWeatherData fromJson(JSONObject forecastObject) throws JSONException, JSONException {



        String day = forecastObject.getString("day");
        String icon = forecastObject.getString("icon");
        String weatherType = forecastObject.getString("weatherType");
        double highTemp = forecastObject.getJSONObject("main").getDouble("temp_max");
        double lowTemp = forecastObject.getJSONObject("main").getDouble("temp_min");


        return new ForecastWeatherData(day, icon, weatherType, highTemp, lowTemp);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }
}