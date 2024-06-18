package com.boiler.weatherwatcher2;

public class WeatherConverter {
    public static int convertKelvinToCelsius(double kelvin) {
        return (int) (kelvin - 273.15);
    }
}
