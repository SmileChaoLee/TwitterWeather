package com.example.twitterweather.util;

import com.example.twitterweather.models.WeatherData;
import com.example.twitterweather.models.WeatherDataList;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class StandardDev {

    private StandardDev() {}

    public static double stdDeviation(ArrayList<Double> dataList) {
        double stdDev = -1.0;
        if (dataList == null) {
            return stdDev;
        }

        int n = dataList.size();
        if (n==0) return -1.0;
        if (n==1) return 0.0;

        double sum = 0.0;
        for (int i=0; i<n; i++) {
             sum += dataList.get(i);
        }

        double ave = sum / n;
        double sum2 = 0.0;
        for (int i=0; i<n; i++) {
            sum2 += Math.pow(dataList.get(i) - ave, 2);
        }

        stdDev = Math.sqrt(sum2/(n-1));

        return stdDev;
    }
}
