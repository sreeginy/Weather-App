package com.sreeginy.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewWeatherDetails extends AppCompatActivity {

    private TextView temperatureTv, weatherTypeTv, rainTv, windSpeedTv, humidityTv, latitudeTv, longitudeTv, sunriseTv, sunsetTv, pressureT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_weather_details);
        temperatureTv = findViewById(R.id.temperatureTV);
        weatherTypeTv = findViewById(R.id.weatherTV);
        rainTv = findViewById(R.id.rainTV);
        windSpeedTv = findViewById(R.id.windSpeedTV);
        humidityTv = findViewById(R.id.humidityTV);
        latitudeTv = findViewById(R.id.latitudeTV);
        longitudeTv = findViewById(R.id.longitudeTV);
        sunriseTv = findViewById(R.id.sunriseTV);
        sunsetTv = findViewById(R.id.sunsetTV);
        pressureT = findViewById(R.id.pressureTV);


        updateUI();
    }
    public void updateUI() {

        // Get the WeatherData object from the Intent extras
        WeatherData weatherData = getIntent().getParcelableExtra("weatherData");
        if (weatherData != null) {
            temperatureTv.setText(weatherData.getmTemperature());
            weatherTypeTv.setText(weatherData.getmWeatherType());
            rainTv.setText(weatherData.getRain() );
            windSpeedTv.setText(weatherData.getWindSpeed());
            humidityTv.setText(weatherData.getHumidity());
            latitudeTv.setText(String.valueOf(weatherData.getLatitude()));
            longitudeTv.setText(String.valueOf(weatherData.getLongitude()));

            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            sunriseTv.setText(timeFormat.format(new Date(weatherData.getSunrise() * 1000)));
            sunsetTv.setText(timeFormat.format(new Date(weatherData.getSunset() * 1000)));

            pressureT.setText(String.valueOf(weatherData.getPressure()));
        }
    }

}




