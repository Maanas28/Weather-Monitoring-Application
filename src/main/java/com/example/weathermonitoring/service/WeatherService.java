package com.example.weathermonitoring.service;

import com.example.weathermonitoring.model.WeatherData;
import com.example.weathermonitoring.model.DailyWeatherSummary;
import com.example.weathermonitoring.repository.WeatherDataRepository;
import com.example.weathermonitoring.repository.DailyWeatherSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private DailyWeatherSummaryRepository dailyWeatherSummaryRepository;

    @Autowired
    private AlertService alertService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.locations}")
    private String[] locations;

    @Value("${weather.api.interval}")
    private long interval;

    @Scheduled(fixedRateString = "${weather.api.interval}")
    public void scheduleWeatherDataRetrieval() {
        for (String location : locations) {
            fetchAndSaveWeatherData(location);
        }
    }

    public WeatherData fetchAndSaveWeatherData(String location) {
        String url = String.format("%s?q=%s&appid=%s", apiUrl, location, apiKey);
        Map<String, Object> response = restTemplate.getForObject(url, HashMap.class);
        if (response != null) {
            Map<String, Object> main = (Map<String, Object>) response.get("main");
            double temp = ((Number) main.get("temp")).doubleValue() - 273.15; // Convert from Kelvin to Celsius
            double feelsLike = ((Number) main.get("feels_like")).doubleValue() - 273.15;
            String weatherCondition = (String) ((Map<String, Object>) ((List<Object>) response.get("weather")).get(0)).get("main");
            long timestamp = ((Number) response.get("dt")).longValue();
            LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

            WeatherData weatherData = new WeatherData();
            weatherData.setLocation(location);
            weatherData.setTemp(temp);
            weatherData.setFeelsLike(feelsLike);
            weatherData.setMain(weatherCondition);
            weatherData.setTimestamp(dateTime);
            weatherDataRepository.save(weatherData);

            updateDailySummary(location, weatherData);
            alertService.checkAndTriggerAlerts(weatherData);

            return weatherData;
        } else {
            return null;
        }
    }

    public WeatherData getCurrentWeather(String location) {
        Optional<WeatherData> weatherDataOpt = weatherDataRepository.findFirstByLocationOrderByTimestampDesc(location);
        if (weatherDataOpt.isPresent()) {
            return weatherDataOpt.get();
        } else {
            return fetchAndSaveWeatherData(location);
        }
    }

    public List<DailyWeatherSummary> getWeatherSummary(String location) {
        List<DailyWeatherSummary> summaries = dailyWeatherSummaryRepository.findByLocationOrderByDateDesc(location);
        if (summaries.isEmpty()) {
            // Fetch current weather data and update summary
            WeatherData weatherData = fetchAndSaveWeatherData(location);
            if (weatherData != null) {
                summaries = dailyWeatherSummaryRepository.findByLocationOrderByDateDesc(location);
            }
        }
        return summaries;
    }

    private void updateDailySummary(String location, WeatherData weatherData) {
        LocalDate date = weatherData.getTimestamp().toLocalDate();
        String dateString = date.format(DateTimeFormatter.ISO_DATE);

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<WeatherData> weatherDataList = weatherDataRepository.findByLocationAndTimestampBetween(location, startOfDay, endOfDay);
        double avgTemp = weatherDataList.stream().mapToDouble(WeatherData::getTemp).average().orElse(0.0);
        double maxTemp = weatherDataList.stream().mapToDouble(WeatherData::getTemp).max().orElse(0.0);
        double minTemp = weatherDataList.stream().mapToDouble(WeatherData::getTemp).min().orElse(0.0);
        String dominantCondition = weatherDataList.stream()
                .map(WeatherData::getMain)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        DailyWeatherSummary dailyWeatherSummary = new DailyWeatherSummary();
        dailyWeatherSummary.setLocation(location);
        dailyWeatherSummary.setDate(dateString);
        dailyWeatherSummary.setAvgTemp(avgTemp);
        dailyWeatherSummary.setMaxTemp(maxTemp);
        dailyWeatherSummary.setMinTemp(minTemp);
        dailyWeatherSummary.setDominantCondition(dominantCondition);
        dailyWeatherSummaryRepository.save(dailyWeatherSummary);
    }
}
