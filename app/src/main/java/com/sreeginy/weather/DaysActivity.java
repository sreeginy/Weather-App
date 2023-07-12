package com.sreeginy.weather;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sreeginy.weather.Adapter.ForecastAdapter;
import com.sreeginy.weather.Model.ForecastWeatherData;

import java.util.ArrayList;

public class DaysActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private WeatherHttpClient weatherHttpClient;

    private Handler forecastHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case WeatherHttpClient.FORECAST_FETCH_SUCCESS:
                    ArrayList<ForecastWeatherData> forecastData = (ArrayList<ForecastWeatherData>) msg.obj;
                    updateForecastUI(forecastData);
                    break;
                case WeatherHttpClient.FORECAST_FETCH_FAILURE:
                    Log.e(TAG, "Forecast fetch failed");
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        recyclerView = findViewById(R.id.forecastRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        forecastAdapter = new ForecastAdapter(new ArrayList<>());
        recyclerView.setAdapter(forecastAdapter);


        weatherHttpClient = new WeatherHttpClient();

        fetchForecastData();
    }

    private void updateForecastUI(ArrayList<ForecastWeatherData> forecastData) {
        forecastAdapter.setItems(forecastData);
        forecastAdapter.notifyDataSetChanged();
    }

    private void fetchForecastData() {
        String cityName = "Zocca"; // Replace with the desired city name
        weatherHttpClient.fetch5DayForecastData(cityName, forecastHandler);
    }


}
