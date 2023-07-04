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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText etCity, etCountry;
    TextView resultTv;
    String apiKey = "0834e2dbfe812be60ce5bb46a74aa17c";
    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Colombo&appid=" + apiKey;

//    private final String url = "https://api.openweathermap.org/data/2.5/weather";
//    private final String appid ="0834e2dbfe812be60ce5bb46a74aa17c";

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.cityEdt);
        etCountry = findViewById(R.id.countryEdt);
        resultTv = findViewById(R.id.resultTv);

    }

    public void getWeatherDetails(View view) {
        String temUrl = "";
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if(city.equals("")) {
            resultTv.setText("City field can not be empty");
        }else {
            if(!country.equals("")) {
                temUrl = apiUrl + "?q=" + city + "," + country + "&appid" + apiKey;
            }else {
                temUrl = apiUrl + "?q=" + city + "&appid" + apiKey;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, temUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response",response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}