# Weather Monitoring Application

## Overview
This project is a weather monitoring application that fetches current weather data and displays weather summaries for specified locations. It uses Spring Boot for the backend and PostgreSQL as the database.

## Prerequisites
- Java 11
- PostgreSQL
- Maven

## Tech Stack
- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL
- **Frontend**: HTML, CSS, JavaScript
- **Build Tool**: Maven

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
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Setting Up the Project

### Local Development
1. Ensure PostgreSQL is installed and running. Create a database named `weatherdb`.

2. Update the `application.properties` file with your database credentials:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/weatherdb
    spring.datasource.username=<your-username>
    spring.datasource.password=<your-password>
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

3. Build the project using Maven:
    ```bash
    ./mvnw clean install
    ```

4. Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```

## Accessing the Application
Open your browser and navigate to [http://localhost:8080](http://localhost:8080).

## API Endpoints
- **GET /weather/current?location={location}**: Fetches the current weather for the specified location.
- **GET /weather/summary?location={location}**: Fetches the weather summary for the specified location.

### Brief on Endpoints
- **Current Weather Endpoint**: This endpoint fetches the real-time weather information for a given location, including temperature, humidity, wind speed, etc.
- **Weather Summary Endpoint**: This endpoint provides a summary of the weather for a given location, potentially including historical data and trends.

## Dependencies
- Java 11
- Spring Boot
- PostgreSQL
- Maven

## Build Instructions
1. Clone the repository:
    ```bash
    git clone <your-github-repo-url>
    cd Zeotap Assignment Application-2
    ```

2. Build the project using Maven:
    ```bash
    ./mvnw clean install
    ```

## Running Tests
To run the tests locally:
```bash
./mvnw test
```

## Contact
For any questions or issues, please contact [maanaskanwar88@gmail.com](mailto:maanaskanwar88@gmail.com).
