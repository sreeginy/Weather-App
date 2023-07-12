package com.sreeginy.weather;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SearchActivity extends AppCompatActivity {

    private TextView temperature, weatherCon, rain, wind, humidity, cityName;
    private ImageView weatherIcon;

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
        ImageView dayBtn = findViewById(R.id.dayBtn);

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

        dayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(SearchActivity.this, DaysActivity.class);
                 startActivity(intent);
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

        weatherHttpClient = new WeatherHttpClient();

        String cityName = getIntent().getStringExtra("City"); // Get the city name from intent
        fetchWeatherData(cityName);
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
}
