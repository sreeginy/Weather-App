package com.sreeginy.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherAPI {

    @GET("weather")
    Call<Example> getweather(@Query("q") String cityname,
                             @Query("apiKey") String apikey);
}
