package com.example.twitterweather.retrofit_package;

import com.example.twitterweather.models.WeatherData;
import com.example.twitterweather.models.WeatherDataList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
    @GET("{future}")
    Call<WeatherData> getWeatherData(@Path("future") String future);
}
