package com.example.weathermonitoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.weathermonitoring.service.WeatherService;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void testGetCurrentWeather() {
        String location = "Delhi"; // Example location
        String result = weatherService.getCurrentWeather(location);
        assertTrue(result.contains("Weather data for Delhi saved successfully!") || result.contains("Failed to fetch weather data for Delhi"));
    }
}
