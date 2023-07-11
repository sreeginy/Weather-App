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
import java.util.ArrayList;

import com.sreeginy.weather.WeatherData;
import com.sreeginy.weather.WeatherHttpClient;

public class SearchActivity extends AppCompatActivity {

    private TextView temperature, weatherCon, rain, wind, humidity,cityName;
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
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("City", newCity);
                startActivity(intent);
                return false;
            }
        });

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        weatherHttpClient = new WeatherHttpClient();
//        initRecyclerView();
    }
    private void fetchWeatherData(String cityName) {
        weatherHttpClient.fetchWeatherData(cityName, weatherHandler);
    }

    private void updateWeatherUI(WeatherData weatherData) {
        cityName.setText(weatherData.getmNameOfCity());
        temperature.setText(weatherData.getmTemperature() + "°");
        weatherCon.setText(weatherData.getmWeatherType());
        rain.setText(weatherData.getRain());
        wind.setText(weatherData.getWindSpeed());
        humidity.setText(weatherData.getHumidity());
        // Set weather icon based on the weather condition
        int weatherIconResId = getResources().getIdentifier(
                weatherData.getmWeatherIcon(), "drawable", getPackageName());
        weatherIcon.setImageResource(weatherIconResId);
    }

//    private void initRecyclerView() {
//        ArrayList<Tomorrow> items = new ArrayList<>();
//
//        items.add(new Tomorrow("Sun", "cloudy", "Strom", 25, 10));
//        items.add(new Tomorrow("Mon", "storm", "Rainny", 75, 30));
//        items.add(new Tomorrow("Tue", "sun", "Cloudy", 55, 5));
//        items.add(new Tomorrow("Wed", "wind", "Strom", 25, 10));
//        items.add(new Tomorrow("Thu", "snowy", "Snow", 67, 2));
//        items.add(new Tomorrow("Fri", "cloudy_3", "Sunny", 10, 5));
//        items.add(new Tomorrow("Sat", "cloudy_sunny", "Storm", 25, 10));
//
//        recyclerView = findViewById(R.id.view2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        adapterTomorrow = new TomorrowAdapter(items);
//        recyclerView.setAdapter(adapterTomorrow);
//    }

}
