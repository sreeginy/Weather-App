package com.sreeginy.weather;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sreeginy.weather.Adapter.TomorrowAdapter;
import com.sreeginy.weather.Model.Tomorrow;
import com.sreeginy.weather.WeatherData;
import com.sreeginy.weather.WeatherHttpClient;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private TextView temperature, weatherCon, rain, wind, humidity, cityName;
    private ImageView weatherIcon;

    private RecyclerView.Adapter adapterTomorrow;
    private RecyclerView recyclerView;

    private WeatherHttpClient weatherHttpClient;

    private Handler weatherHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case WeatherHttpClient.WEATHER_FETCH_SUCCESS:
                    WeatherData weatherData = (WeatherData) msg.obj;
                    updateWeatherUI(weatherData);
                    break;
                case WeatherHttpClient.WEATHER_FETCH_FAILURE:
                    Log.e(TAG, "Weather fetch failed");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText editText = findViewById(R.id.searchCity);
        ImageView backButton = findViewById(R.id.backBtn);

        temperature = findViewById(R.id.temperature);
        weatherCon = findViewById(R.id.weatherCon);
        rain = findViewById(R.id.rain);
        wind = findViewById(R.id.wind);
        humidity = findViewById(R.id.humidity);
        cityName = findViewById(R.id.cityName);
        weatherIcon = findViewById(R.id.weatherIcon);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity = editText.getText().toString();
                fetchWeatherData(newCity);
                return false;
            }
        });

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        weatherHttpClient = new WeatherHttpClient();

        String cityName = getIntent().getStringExtra("City"); // Get the city name from intent
        fetchWeatherData(cityName);

        initRecyclerView();
    }

    private void fetchWeatherData(String cityName) {
        weatherHttpClient.fetchWeatherData(cityName, weatherHandler);
    }

    private void updateWeatherUI(WeatherData weatherData) {
        cityName.setText(weatherData.getmNameOfCity());
        temperature.setText(weatherData.getmTemperature() + "°");
        weatherCon.setText("Mostly " + weatherData.getmWeatherType());
        rain.setText(weatherData.getRain() + " mm");
        wind.setText(weatherData.getWindSpeed() + " km/h");
        humidity.setText(weatherData.getHumidity() + "%");
        // Set weather icon based on the weather condition
        int weatherIconResId = getResources().getIdentifier(
                weatherData.getmWeatherIcon(), "drawable", getPackageName());
        weatherIcon.setImageResource(weatherIconResId);
    }

    private void initRecyclerView() {
        ArrayList<Tomorrow> items = new ArrayList<>();

        // Call the API to fetch tomorrow's weather data
        String cityName = getIntent().getStringExtra("City");
        WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
        weatherHttpClient.fetchWeatherData(cityName, new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case WeatherHttpClient.WEATHER_FETCH_SUCCESS:
                        WeatherData weatherData = (WeatherData) msg.obj;
                        // Assuming the API response contains tomorrow's weather data
                        // You need to adapt this part based on the API response structure
                        Tomorrow tomorrowWeather = new Tomorrow(
                                "Tomorrow",
                                weatherData.getmWeatherIcon(),
                                weatherData.getmWeatherType(),
                                weatherData.getMaxTemperature(),
                                weatherData.getMinTemperature()
                        );
                        items.add(tomorrowWeather);
                        adapterTomorrow.notifyDataSetChanged();
                        break;
                    case WeatherHttpClient.WEATHER_FETCH_FAILURE:
                        Log.e(TAG, "Failed to fetch tomorrow's weather data");
                        break;
                }
            }
        });

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapterTomorrow = new TomorrowAdapter(items);
        recyclerView.setAdapter(adapterTomorrow);
    }

}
