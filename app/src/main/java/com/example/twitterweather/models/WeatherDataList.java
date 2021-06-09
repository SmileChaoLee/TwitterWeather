package com.example.twitterweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherDataList implements Parcelable {

    @SerializedName("weatherData")
    private ArrayList<WeatherData> weatherData;

    public WeatherDataList() {
        weatherData = new ArrayList<>();
    }

    public WeatherDataList(ArrayList<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }

    public ArrayList<WeatherData> getWeathers() {
        return weatherData;
    }

    public void setWeathers(ArrayList<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.weatherData);
    }

    public void readFromParcel(Parcel source) {
        this.weatherData = source.createTypedArrayList(WeatherData.CREATOR);
    }

    protected WeatherDataList(Parcel in) {
        this.weatherData = in.createTypedArrayList(WeatherData.CREATOR);
    }

    public static final Creator<WeatherDataList> CREATOR = new Creator<WeatherDataList>() {
        @Override
        public WeatherDataList createFromParcel(Parcel source) {
            return new WeatherDataList(source);
        }

        @Override
        public WeatherDataList[] newArray(int size) {
            return new WeatherDataList[size];
        }
    };
}
