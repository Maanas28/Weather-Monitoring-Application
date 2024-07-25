package com.example.weathermonitoring.repository;

import com.example.weathermonitoring.model.DailyWeatherSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyWeatherSummaryRepository extends JpaRepository<DailyWeatherSummary, Long> {

    // New method to find daily weather summaries for a specific location
    List<DailyWeatherSummary> findByLocationOrderByDateDesc(String location);
}
