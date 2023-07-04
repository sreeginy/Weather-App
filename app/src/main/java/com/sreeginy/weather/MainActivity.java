package com.sreeginy.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        weatherAPI myapi = retrofit.create(weatherAPI.class);
        Call<Example> examplecall = myapi.getweather(etCity.getText().toString().trim(),apiKey);
        examplecall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "Please Enter a valid city", Toast.LENGTH_SHORT).show();
                } else if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                Example mydata = response.body();
                Main main = mydata.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int)(temp-273.15);
                resultTv.setText(String.valueOf(temperature)+"C");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




//    public void getWeatherDetails(View view) {
//        String temUrl = "";
//        String city = etCity.getText().toString().trim();
//        if(city.equals("")) {
//            resultTv.setText("City field can not be empty");
//        }else {
//            if(!country.equals("")) {
//                temUrl = apiUrl + "?q=" + city + "," + country + "&appid" + apiKey;
//            }else {
//                temUrl = apiUrl + "?q=" + city + "&appid" + apiKey;
//            }
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, temUrl, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.d("response",response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//        }
//}
}