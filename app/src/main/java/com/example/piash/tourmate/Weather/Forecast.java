package com.example.piash.tourmate.Weather;

/**
 * Created by PIASH on 01-May-17.
 */

public class Forecast {

    String minTemp,maxTemp,sunRise,sunSet,date;

    public Forecast(String date,String minTemp, String maxTemp, String sunRise, String sunSet) {
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinTemp() {

        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }
}
