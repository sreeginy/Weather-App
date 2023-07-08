package com.sreeginy.weather;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etCity;
    TextView date;
    String apiKey = "0834e2dbfe812be60ce5bb46a74aa17c";
    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Colombo&appid=" + apiKey;

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

//    private final String url = "https://api.openweathermap.org/data/2.5/weather";
//    private final String appid ="0834e2dbfe812be60ce5bb46a74aa17c";

//    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    TextView mNameofCity, mweatherState, mTemperature;
    ImageView mWeatherIcon;
    Button searchBtn;
    LocationManager mlocationManager;
    LocationListener mlocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mweatherState = findViewById(R.id.weatherCon);
        mTemperature = findViewById(R.id.temperature);
        mWeatherIcon = findViewById(R.id.weatherIcon);
        searchBtn = findViewById(R.id.searchBtn);
        mNameofCity = findViewById(R.id.cityName);
        date = findViewById(R.id.date);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String curretndate = format.format(new Date());
        date.setText(curretndate);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String city = intent.getStringExtra("City");
        if (city != null) {
            apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            getWeatherForNewCity(city);
        } else {
            getWeatherForCurrentLocation();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//        String city = intent.getStringExtra("City");
//        if (city != null) {
//            getWeatherForNewCity(city);
//        } else {
//            getWeatherForCurrentLocation();
//        }
//    }

//    private void getWeatherForNewCity(String city) {
//        RequestParams params = new RequestParams();
//        params.put("q", city);
//        params.put("appid", apiKey);
//        letsdoSomeNetworking(params);
//    }

    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", apiKey);
        letsdoSomeNetworking(params);
    }

    private void getWeatherForCurrentLocation() {
        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", apiKey);
                letsdoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LocationListener.super.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        mlocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mlocationListener);

    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Locationget Successfully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {

            }
        }
    }

    private void letsdoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(apiUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(MainActivity.this, "Data Get Success", Toast.LENGTH_SHORT).show();
                com.sreeginy.weather.WeatherData weatherData = null;
                try {
                    weatherData = com.sreeginy.weather.WeatherData.fromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateUI(weatherData);
            }
        });
    }
//    public void updateUI(WeatherData weather) {
//        mTemperature.setText(weather.mTemperature);
//        mNameofCity.setText(weather.mNameofCity);
//
//        if (weather.weatherType != null) {
//            mweatherState.setText(weather.weatherType);
//        } else {
//            mweatherState.setText("N/A");
//        }
//
//        int resourceID = getResources().getIdentifier(weather.mWeatherIcon, "drawable", getPackageName());
//        if (resourceID != 0) {
//            mWeatherIcon.setImageResource(resourceID);
//        } else {
////            mWeatherIcon.setImageResource(R.drawable.weatherIcon);
//        }
//    }
    public void updateUI(WeatherData weather) {
        mTemperature.setText(weather.mTemperature);
        mNameofCity.setText(weather.mNameofCity);
        mweatherState.setText(weather.weatherType);
        int resourceID = getResources().getIdentifier(weather.mWeatherIcon, "drawable", getPackageName());
        mWeatherIcon.setImageResource(resourceID);
    }


    protected void onPause() {
        super.onPause();
        if(mlocationManager != null) {
            mlocationManager.removeUpdates(mlocationListener);
        }
    }
}

//    public void getWeather(View v) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//    }
