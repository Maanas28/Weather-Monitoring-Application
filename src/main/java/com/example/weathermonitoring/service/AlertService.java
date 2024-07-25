package com.example.weathermonitoring.service;

import com.example.weathermonitoring.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlertService {

    @Autowired
    private EmailService emailService;

    @Value("${alert.temperature.threshold}")
    private double temperatureThreshold;

    @Value("${alert.temperature.duration}")
    private int temperatureDuration;

    private Map<String, Integer> locationTempExceedCount = new HashMap<>();

    public void checkAndTriggerAlerts(WeatherData weatherData) {
        if (weatherData.getTemp() > temperatureThreshold) {
            locationTempExceedCount.put(weatherData.getLocation(), locationTempExceedCount.getOrDefault(weatherData.getLocation(), 0) + 1);
        } else {
            locationTempExceedCount.put(weatherData.getLocation(), 0);
        }

        if (locationTempExceedCount.get(weatherData.getLocation()) >= temperatureDuration) {
            sendAlert(weatherData);
            locationTempExceedCount.put(weatherData.getLocation(), 0);
        }
    }

    private void sendAlert(WeatherData weatherData) {
        String subject = "Weather Alert for " + weatherData.getLocation();
        String body = "Temperature has exceeded " + temperatureThreshold + " degrees Celsius for " + temperatureDuration + " consecutive updates.\n" +
                "Current temperature: " + weatherData.getTemp() + "°C";
        emailService.sendEmail("recipient@example.com", subject, body);
    }
}
