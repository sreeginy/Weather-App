package com.sreeginy.weather;


import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {

    public String mTemperature;
    public String mWeatherIcon;
    public String mNameofCity;
    public String weatherType;
    private int condition;


    public static WeatherData fromJson(JSONObject jsonObject) throws JSONException {


        try {
            WeatherData weatherData = new WeatherData();
            weatherData.mNameofCity = jsonObject.getString("name");
            weatherData.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.weatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.mWeatherIcon = updateWeatherIcon(weatherData.condition);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue = (int)Math.rint(tempResult);
            weatherData.mTemperature = Integer.toString(roundedValue);
            return weatherData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


//    private static String updateWeatherIcon(int condition) {
//        if (condition >= 200 && condition <= 232) {
//            return "thunderstorm";
//        } else if (condition >= 300 && condition <= 321) {
//            return "drizzle";
//        } else if (condition >= 500 && condition <= 531) {
//            return "rain";
//        } else if (condition >= 600 && condition <= 622) {
//            return "snow";
//        } else if (condition >= 701 && condition <= 781) {
//            return "mist";
//        } else if (condition == 800) {
//            return "clear";
//        } else if (condition >= 801 && condition <= 804) {
//            return "clouds";
//        } else {
//            return "unknown";
//        }
//    }

    private static String updateWeatherIcon(int condition) {
        if(condition >= 0 && condition <=300) {
            return "thunderstrom1";
        } else if (condition >= 300 && condition <=500) {
            return "lightrain";
        } else if (condition >= 500 && condition <=600) {
            return "shower";
        } else if (condition >= 600 && condition <=700) {
            return "snow2";
        } else if (condition >= 701 && condition <=771) {
            return "fog";
        } else if (condition >= 772 && condition <=800) {
            return "overcast";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <=804) {
            return "cloudy";
        } else if (condition >= 900 && condition <=902) {
            return "thunderstrom1";
        } else if (condition == 903) {
            return "snow1";
        } else if (condition == 904) {
            return  "sunny";
        } else if (condition >= 905 && condition <=1000) {
            return "thunderstrom2";
        } return "dunno";
    }


}