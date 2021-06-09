package com.example.twitterweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WeatherData implements Parcelable {
	@SerializedName("coord")
    private Coord coord;

	@SerializedName("weather")
    private Weather weather;

	@SerializedName("wind")
    private Wind wind;

	@SerializedName("clouds")
    private Clouds clouds;

	@SerializedName("rain")
    private Rain rain;

	@SerializedName("name")
    private String name;

	public WeatherData() {
		coord = new Coord();
		weather = new Weather();
		wind = new Wind();
		clouds = new Clouds();
		rain = new Rain();
		name = "";
	}

	public WeatherData(Coord coord, Weather weather, Wind wind, Clouds clouds, Rain rain, String name) {
		this.coord = coord;
		this.weather = weather;
		this.wind = wind;
		this.clouds = clouds;
		this.rain = rain;
		this.name = name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.coord, flags);
		dest.writeParcelable(this.weather, flags);
		dest.writeParcelable(this.wind, flags);
		dest.writeParcelable(this.clouds, flags);
		dest.writeParcelable(this.rain, flags);
		dest.writeString(this.name);
	}

	public void readFromParcel(Parcel source) {
		this.coord = source.readParcelable(Coord.class.getClassLoader());
		this.weather = source.readParcelable(Weather.class.getClassLoader());
		this.wind = source.readParcelable(Wind.class.getClassLoader());
		this.clouds = source.readParcelable(Clouds.class.getClassLoader());
		this.rain = source.readParcelable(Rain.class.getClassLoader());
		this.name = source.readString();
	}

	protected WeatherData(Parcel in) {
		this.coord = in.readParcelable(Coord.class.getClassLoader());
		this.weather = in.readParcelable(Weather.class.getClassLoader());
		this.wind = in.readParcelable(Wind.class.getClassLoader());
		this.clouds = in.readParcelable(Clouds.class.getClassLoader());
		this.rain = in.readParcelable(Rain.class.getClassLoader());
		this.name = in.readString();
	}

	public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
		@Override
		public WeatherData createFromParcel(Parcel source) {
			return new WeatherData(source);
		}

		@Override
		public WeatherData[] newArray(int size) {
			return new WeatherData[size];
		}
	};

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Rain getRain() {
		return rain;
	}

	public void setRain(Rain rain) {
		this.rain = rain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
