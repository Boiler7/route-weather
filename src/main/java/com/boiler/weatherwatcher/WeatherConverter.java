package com.boiler.weatherwatcher;

public class WeatherConverter {
    public static int convertKelvinToCelsius(double kelvin) {
        return (int) (kelvin - 273.15);
    }
}
