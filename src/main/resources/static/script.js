let currentWeatherData = null;
let weatherSummaryData = null;
let currentUnit = 'celsius';

function fetchWeather() {
    const location = document.getElementById('locationInput').value;
    if (location) {
        fetchCurrentWeather(location);
        fetchWeatherSummary(location);
    } else {
        alert('Please enter a location.');
    }
}

function fetchCurrentWeather(location) {
    fetch(`http://localhost:8080/weather/current?location=${location}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            currentWeatherData = data;
            displayCurrentWeather();
        })
        .catch(error => console.error('Error fetching current weather:', error));
}

function fetchWeatherSummary(location) {
    fetch(`http://localhost:8080/weather/summary?location=${location}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            weatherSummaryData = data;
            displayWeatherSummary();
        })
        .catch(error => console.error('Error fetching weather summary:', error));
}

function updateTemperatureUnit() {
    currentUnit = document.getElementById('unitSelect').value;
    displayCurrentWeather();
    displayWeatherSummary();
}

function displayCurrentWeather() {
    if (currentWeatherData) {
        let temp = convertTemperature(currentWeatherData.temp);
        let feelsLike = convertTemperature(currentWeatherData.feelsLike);
        document.getElementById('currentWeather').innerText = `Temperature: ${temp}°, Feels Like: ${feelsLike}°, Condition: ${currentWeatherData.main}`;
    }
}

function displayWeatherSummary() {
    if (weatherSummaryData && weatherSummaryData.length > 0) {
        let summaryHtml = '<ul>';
        weatherSummaryData.forEach(summary => {
            let avgTemp = convertTemperature(summary.avgTemp);
            let maxTemp = convertTemperature(summary.maxTemp);
            let minTemp = convertTemperature(summary.minTemp);
            summaryHtml += `<li>Date: ${summary.date}, Avg Temp: ${avgTemp}°, Max Temp: ${maxTemp}°, Min Temp: ${minTemp}°, Condition: ${summary.dominantCondition}</li>`;
        });
        summaryHtml += '</ul>';
        document.getElementById('weatherSummary').innerHTML = summaryHtml;
    } else {
        document.getElementById('weatherSummary').innerText = 'No data available';
    }
}

function convertTemperature(temp) {
    if (currentUnit === 'kelvin') {
        return (temp + 273.15).toFixed(2); // Convert from Celsius to Kelvin
    } else {
        return temp.toFixed(2); // Default is Celsius
    }
}
