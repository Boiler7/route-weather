package com.boiler.weatherwatcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class WeatherController {

    private final ApiWeatherService weatherService;
    private final RequestTrackingService requestTrackingService;

    @Value("${googlemaps.api.key}")
    private String googleMapsApiKey;

    public WeatherController(ApiWeatherService weatherService, RequestTrackingService requestTrackingService) {
        this.weatherService = weatherService;
        this.requestTrackingService = requestTrackingService;
    }

    @GetMapping("/")
    public String showPage(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "index";
    }

    @GetMapping("/weather")
    @ResponseBody
    public HashMap<String, Object> getWeather(HttpServletRequest request, @RequestParam String city1, @RequestParam String city2) {
        String ipAddress = request.getRemoteAddr();

        if (!requestTrackingService.isRequestAllowed(ipAddress)) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("error", "You have exceeded the daily limit of 5 requests.");
            response.put("remainingRequests", 0);
            return response;
        }

        HashMap<String, Object> weatherData = new HashMap<>();

        weatherData.put("city1", weatherService.getWeatherAndModifyTemperature(city1));
        weatherData.put("city2", weatherService.getWeatherAndModifyTemperature(city2));

        return weatherData;
    }
}
