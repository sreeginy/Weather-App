package com.sreeginy.weather;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sreeginy.weather.WeatherData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {

    private static final String TAG = WeatherHttpClient.class.getSimpleName();

    public static final int WEATHER_FETCH_SUCCESS = 1;
    public static final int WEATHER_FETCH_FAILURE = 2;

    private static final String API_KEY = "0834e2dbfe812be60ce5bb46a74aa17c";
    private static final String API_BASE_URL = "api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid={0834e2dbfe812be60ce5bb46a74aa17c}";

    public void fetchWeatherData(String cityName, final Handler weatherHandler) {
        String apiUrl = API_BASE_URL + "?q=" + cityName + "&appid=" + API_KEY;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        WeatherData weatherData = parseWeatherData(response.toString());
                        if (weatherData != null) {
                            Message successMessage = weatherHandler.obtainMessage(WEATHER_FETCH_SUCCESS, weatherData);
                            successMessage.sendToTarget();
                        } else {
                            Log.e(TAG, "Failed to parse weather data");
                            weatherHandler.sendEmptyMessage(WEATHER_FETCH_FAILURE);
                        }
                    } else {
                        Log.e(TAG, "Weather API request failed with response code: " + responseCode);
                        weatherHandler.sendEmptyMessage(WEATHER_FETCH_FAILURE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    weatherHandler.sendEmptyMessage(WEATHER_FETCH_FAILURE);
                }
            }
        }).start();
    }

    private WeatherData parseWeatherData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return WeatherData.fromJson(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
