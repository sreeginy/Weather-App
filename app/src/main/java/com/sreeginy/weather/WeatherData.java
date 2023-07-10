package com.sreeginy.weather;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {
    public String mNameOfCity;
    public String mTemperature;
    public String mWeatherType;
    public String mWeatherIcon;
    public String rain;
    public String windSpeed;
    public String humidity;
    public int condition;

    // Rest of the class code
    public static WeatherData fromJson(JSONObject jsonObject) throws JSONException {
        try {
            WeatherData weatherData = new WeatherData();

            weatherData.mNameOfCity = jsonObject.getString("name");

            weatherData.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mWeatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.mWeatherIcon = updateWeatherIcon(weatherData.condition);

            double temperature = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundedValue = (int) Math.rint(temperature);
            weatherData.mTemperature = Integer.toString(roundedValue);

            JSONObject rainObject = jsonObject.optJSONObject("rain");
            weatherData.rain = rainObject != null ? rainObject.getString("3h") : "0";
            weatherData.windSpeed = jsonObject.getJSONObject("wind").getString("speed");
            weatherData.humidity = jsonObject.getJSONObject("main").getString("humidity");

            return weatherData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition) {
        if (condition >= 0 && condition <= 300) {
            return "thunderstorm1";
        } else if (condition >= 300 && condition <= 500) {
            return "lightrain";
        } else if (condition >= 500 && condition <= 600) {
            return "shower";
        } else if (condition >= 600 && condition <= 700) {
            return "snow2";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition <= 800) {
            return "overcast";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy";
        } else if (condition >= 900 && condition <= 902) {
            return "thunderstorm1";
        } else if (condition == 903) {
            return "snow1";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "thunderstorm2";
        }
        return "dunno";
    }
}
