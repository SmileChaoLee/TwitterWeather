package com.example.twitterweather;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterweather.models.WeatherData;
import com.example.twitterweather.retrofit_package.RetrofitApiInterface;
import com.example.twitterweather.retrofit_package.RetrofitClient;
import com.example.twitterweather.util.StandardDev;
import com.example.twitterweather.util.TemperatureConverter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getName();
    private final String BASE_URL = "https://twitter-code-challenge.s3.amazonaws.com/";

    private final String WeatherDataListKey = "WeatherDataList";
    private final String CurrentTemperatureKey = "CurrentTemperature";
    private final String CurrentWindSpeedKey = "CurrentWindSpeed";
    private final String CurrentCloudinessKey = "CurrentCloudinessKey";
    private final String StandardDeviationKey = "StandardDeviation";

    private TextView mTemperatureView;
    private TextView mWindSpeedView;
    private ImageView mCloudinessImageView;
    private TextView mStandardDeviationView;
    private ArrayList<WeatherData> mWeatherDataList;
    private double mCurrentTemperature = 0.0;
    private double mCurrentWindSpeed = 0.0;
    private double mCurrentCloudiness = 0.0;
    private double mStandardDeviation = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherDataList = new ArrayList<>();

        mTemperatureView = findViewById(R.id.temperature);
        mTemperatureView.setText(getString(R.string.temperature, mCurrentTemperature, TemperatureConverter.celsiusToFahrenheit((float)mCurrentTemperature)));
        mWindSpeedView = findViewById(R.id.wind_speed);
        mCloudinessImageView = findViewById(R.id.cloudinessImageView);
        mStandardDeviationView = findViewById(R.id.standardDeviation);
        mStandardDeviationView.setText("Unknown");

        Button fetchButton = findViewById(R.id.fetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitRetrieveWeatherDataList getWeatherDataList = new RetrofitRetrieveWeatherDataList(MainActivity.this, false);
                mWeatherDataList.clear();
                for (int i=1; i<=5; i++) {
                    String param = "future_"+i+".json";
                    Log.d(TAG, "param = " + param);
                    getWeatherDataList.startRetrieve(param);
                }
            }
        });

        if (savedInstanceState == null) {
            RetrofitRetrieveWeatherDataList getWeatherDataList = new RetrofitRetrieveWeatherDataList(MainActivity.this, true);
            getWeatherDataList.startRetrieve("current.json");
        } else {
            mWeatherDataList = savedInstanceState.getParcelableArrayList(WeatherDataListKey);
            mCurrentTemperature = savedInstanceState.getDouble(CurrentTemperatureKey);
            mTemperatureView.setText(getString(R.string.temperature, mCurrentTemperature, TemperatureConverter.celsiusToFahrenheit((float)mCurrentTemperature)));
            mCurrentWindSpeed = savedInstanceState.getDouble(CurrentWindSpeedKey);
            mWindSpeedView.setText(String.valueOf(mCurrentWindSpeed));
            mCurrentCloudiness = savedInstanceState.getDouble(CurrentCloudinessKey);
            if (mCurrentCloudiness > 50.0) {
                mCloudinessImageView.setImageResource(R.mipmap.ic_launcher);
            }
            mStandardDeviation = savedInstanceState.getDouble(StandardDeviationKey);
            mStandardDeviationView.setText(String.valueOf(mStandardDeviation));
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "MyActivity.onSaveInstanceState() is called");
        outState.putDouble(CurrentTemperatureKey, mCurrentTemperature);
        outState.putDouble(CurrentWindSpeedKey, mCurrentWindSpeed);
        outState.putDouble(CurrentCloudinessKey, mCurrentCloudiness);
        outState.putDouble(StandardDeviationKey, mStandardDeviation);
        outState.putParcelableArrayList(WeatherDataListKey, mWeatherDataList);
        super.onSaveInstanceState(outState);
    }

    private class RetrofitRetrieveWeatherDataList implements Callback<WeatherData> {

        private final String TAG = new String("RetrofitRetrieveWeatherDataList");
        private final Context mContext;
        private final boolean mIsCurrent;
        public RetrofitRetrieveWeatherDataList(Context context, boolean isCurrent) {
            mContext = context;
            mIsCurrent = isCurrent;
        }

        public void startRetrieve(String param) {
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();     // get Retrofit client
            RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
            Call<WeatherData> call = retrofitApiInterface.getWeatherData(param);
            call.enqueue(this);
        }

        @Override
        public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
            ArrayList<WeatherData> weathersList= new ArrayList<>();
            WeatherData weatherData = null;
            if (response.isSuccessful()) {
                weatherData = response.body();
                weathersList.add(weatherData);
                Log.d(TAG, "Succeeded to retrieve weather");
            } else {
                Log.d(TAG, "Failed to retrieve weather");
            }

            if (weatherData != null) {
                mWeatherDataList.add(weatherData);
                Log.d(TAG, "mWeatherList.size() = " + mWeatherDataList.size());
                if (mIsCurrent) {
                    mCurrentTemperature = weatherData.getWeather().getTemp();
                    mTemperatureView.setText(getString(R.string.temperature, mCurrentTemperature, TemperatureConverter.celsiusToFahrenheit((float) mCurrentTemperature)));
                    mCurrentWindSpeed = weatherData.getWind().getSpeed();
                    mWindSpeedView.setText(String.valueOf(mCurrentWindSpeed));
                    mCurrentCloudiness = weatherData.getClouds().getCloudiness();
                    if (mCurrentCloudiness > 50.0) {
                        mCloudinessImageView.setImageResource(R.mipmap.ic_launcher);
                    }
                } else {
                    ArrayList<Double> tempList = new ArrayList<>();
                    for (int i=0; i<mWeatherDataList.size(); i++) {
                        tempList.add(mWeatherDataList.get(i).getWeather().getTemp());
                    }
                    mStandardDeviation = StandardDev.stdDeviation(tempList);
                    mStandardDeviationView.setText(String.valueOf(mStandardDeviation));
                }
            }

            // myRecyclerViewAdapter = new SingerTypesRecyclerViewAdapter(textFontSize, singerTypesList.getSingerTypes());
            // singerTypesRecyclerView.setAdapter(myRecyclerViewAdapter);
            // singerTypesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }

        @Override
        public void onFailure(Call<WeatherData> call, Throwable t) {
            System.out.println("RetrofitRetrieveSingerType --> " + t.toString());
            mWeatherDataList = new ArrayList<>();
        }
    }
}
