package com.sreeginy.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etCity;
    TextView resultTv;
    String apiKey = "0834e2dbfe812be60ce5bb46a74aa17c";
    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Colombo&appid=" + apiKey;

//    private final String url = "https://api.openweathermap.org/data/2.5/weather";
//    private final String appid ="0834e2dbfe812be60ce5bb46a74aa17c";

//    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.cityEdt);
        resultTv = findViewById(R.id.resultTv);

    }
    public void getWeather(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}