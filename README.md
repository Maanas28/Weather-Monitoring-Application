# Weather Monitoring Application

## Overview
This project is a weather monitoring application that fetches current weather data and displays weather summaries for specified locations. It uses Spring Boot for the backend, PostgreSQL as the database, and Docker for containerization.

## Prerequisites
- Docker
- Docker Compose (if using the `docker-compose.yml` file)

## Project Structure
```
Zeotap Assignment Application-2/
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── weathermonitoring/
│   │   │               ├── config/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               ├── service/
│   │   │               └── WeatherMonitoringApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── index.html
│   │           ├── script.js
│   │           └── style.css
├── .gitattributes
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Setting Up the Project

### Using Docker Compose (Recommended)
1. Ensure Docker is installed and running on your machine.
2. Navigate to the project directory.
3. Run the following command to build and start the application:
   ```sh
   docker-compose up --build
   ```

### Building and Running the Docker Image Manually
1. Navigate to the project directory.
2. Build the Docker image:
   ```sh
   docker build -t weather-monitoring-app .
   ```
3. Run the Docker container:
   ```sh
   docker run -d -p 8080:8080 weather-monitoring-app
   ```

### Local Development
If you prefer to run the application locally without Docker, follow these steps:

1. Ensure PostgreSQL is installed and running. Create a database named `weatherdb`.
2. Update the `application.properties` file with your database credentials.
3. Build the project using Maven:
   ```sh
   ./mvnw clean install
   ```
4. Run the application:
   ```sh
   ./mvnw spring-boot:run
   ```

## Accessing the Application
Open your browser and navigate to `http://localhost:8080`.

## API Endpoints
- **GET /weather/current?location={location}**: Fetches the current weather for the specified location.
- **GET /weather/summary?location={location}**: Fetches the weather summary for the specified location.

## Design Choices
- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **Frontend**: Plain HTML, CSS, JavaScript
- **Containerization**: Docker

## Dependencies
- Java 11
- Spring Boot
- PostgreSQL
- Docker
- Maven

## Build Instructions
1. Clone the repository:
   ```sh
   git clone <your-github-repo-url>
   cd Zeotap Assignment Application-2
   ```
2. Build the project using Maven:
   ```sh
   ./mvnw clean install
   ```

## Running Tests
To run the tests locally:
```sh
./mvnw test
```

## Contact
For any questions or issues, please contact (mailto:maanaskanwar88@gmail.com).
