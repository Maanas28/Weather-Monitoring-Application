package com.example.weathermonitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.weathermonitoring.model.WeatherData;
import com.example.weathermonitoring.model.DailyWeatherSummary;
import com.example.weathermonitoring.service.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "http://localhost:63342") // Allow CORS for the frontend origin
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public WeatherData getCurrentWeather(@RequestParam String location) {
        return weatherService.getCurrentWeather(location);
    }

    @GetMapping("/summary")
    public List<DailyWeatherSummary> getWeatherSummary(@RequestParam String location) {
        return weatherService.getWeatherSummary(location);
    }
}
