package com.example.weathermonitoring.repository;

import com.example.weathermonitoring.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    @Query("SELECT w FROM WeatherData w ORDER BY w.timestamp DESC")
    Optional<WeatherData> findFirstByOrderByTimestampDesc();

    // Derived query method to find the latest weather data for a specific location
    Optional<WeatherData> findFirstByLocationOrderByTimestampDesc(String location);

    // Method to find weather data between two timestamps for a specific location
    List<WeatherData> findByLocationAndTimestampBetween(String location, LocalDateTime start, LocalDateTime end);
}
