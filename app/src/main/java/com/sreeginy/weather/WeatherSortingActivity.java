package com.sreeginy.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sreeginy.weather.Adapter.WeatherAdapter;
import com.sreeginy.weather.Model.ForecastWeatherData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WeatherSortingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private List<ForecastWeatherData> forecastDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_sorting);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        forecastDataList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(forecastDataList);
        recyclerView.setAdapter(weatherAdapter);

        fetchData();
    }

    private void fetchData() {
        WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
        weatherHttpClient.fetch7DayForecastData("Jaffna", new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WeatherHttpClient.FORECAST_FETCH_SUCCESS) {
                    ArrayList<ForecastWeatherData> forecastData = (ArrayList<ForecastWeatherData>) msg.obj;
                    updateData(forecastData);
                } else {
                    Log.e("WeatherSortingActivity", "Failed to fetch forecast data");
                }
            }
        });
    }

    private void updateData(ArrayList<ForecastWeatherData> forecastData) {
        forecastDataList.clear();
        forecastDataList.addAll(forecastData);

        // Sort the data based on the date in ascending order
        Collections.sort(forecastDataList, new Comparator<ForecastWeatherData>() {
            @Override
            public int compare(ForecastWeatherData data1, ForecastWeatherData data2) {
                return data1.getDate().compareTo(data2.getDate());
            }
        });

        weatherAdapter.notifyDataSetChanged();
    }
}