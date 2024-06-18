package com.boiler.weatherwatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Service
public class ApiWeatherService {
    private final WebClient webClient = WebClient.builder().build();

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public HashMap<String, Object> getWeather(String city) {
        return webClient.get()
                .uri(apiUrl, uri -> uri
                        .queryParam("q", city)
                        .queryParam("cnt", 5)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();
    }

    public HashMap<String, Object> getWeatherAndModifyTemperature(String city) {
        HashMap<String, Object> weatherData = getWeather(city);

        if (weatherData.containsKey("main")) {
            HashMap<String, Object> main = (HashMap<String, Object>) weatherData.get("main");
            if (main.containsKey("temp")) {
                main.put("temp", WeatherConverter.convertKelvinToCelsius((Double) main.get("temp")));
            }
        }
        return weatherData;
    }
}
