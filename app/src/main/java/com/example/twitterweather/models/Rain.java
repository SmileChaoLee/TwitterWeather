package com.example.twitterweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rain implements Parcelable {
    @SerializedName("thereH")
    private double thereH;

    public Rain() {
        thereH = 0.0;
    }
    public Rain(double thereH) {
        this.thereH = thereH;
    }

    public double getThereH() {
        return thereH;
    }

    public void setThereH(double thereH) {
        this.thereH = thereH;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.thereH);
    }

    public void readFromParcel(Parcel source) {
        this.thereH = source.readDouble();
    }

    protected Rain(Parcel in) {
        this.thereH = in.readDouble();
    }

    public static final Creator<Rain> CREATOR = new Creator<Rain>() {
        @Override
        public Rain createFromParcel(Parcel source) {
            return new Rain(source);
        }

        @Override
        public Rain[] newArray(int size) {
            return new Rain[size];
        }
    };
}
