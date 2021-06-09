package com.example.twitterweather;

import com.example.twitterweather.util.StandardDev;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void stdDevWithSize1_isCorrect() {
        ArrayList<Double> tempList = new ArrayList<>();
        tempList.add(1.5);
        double stdDev = StandardDev.stdDeviation(tempList);
        assertEquals(0.0, stdDev, 0.0);
    }
    @Test
    public void stdDevWithSize0_isCorrect() {
        ArrayList<Double> tempList = new ArrayList<>();
        double stdDev = StandardDev.stdDeviation(tempList);
        assertEquals(-1.0, stdDev, 0.0);
    }
    @Test
    public void stdDevWithNull_isCorrect() {
        ArrayList<Double> tempList = null;
        double stdDev = StandardDev.stdDeviation(tempList);
        assertEquals(-1.0, stdDev, 0.0);
    }
}