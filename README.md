# TrueLayer Challenge

Pokedex with fun translations.

## Production Considerations

For a production-ready system, I would implement the following improvements:

- **Retry Logic**:
   - If external API calls fail due to transient issues, we should retry with exponential backoff to improve resilience.

- **Non-blocking Calls**:
   - The current implementation uses blocking calls, which can limit scalability.

- **Caching**:
   - API responses rarely change, so caching can reduce API calls and improve performance.

- **Unit Tests**:
   - Add unit tests for the controller and API clients.

- **Rate Limiting**:
   - Protect external API usage with rate limiting (e.g. API Gateway).

## Prerequisites

Before running the application, make sure you have the following installed on your system:

- Java Development Kit (JDK) 17 or later
- Apache Maven (for building the application)

## Getting Started

Follow the steps below to run the Spring Boot application:

1. **Clone the Repository**: Clone this repository to your local machine using the following command:

    ```bash
    git clone https://github.com/popclom/truelayer-challenge.git
    cd truelayer-challenge
    ```

2. **Build the Application**: Build the application using Maven (use `mvnw` instead of `mvn` if on Windows):

    ```bash
    mvn clean package
    ```

3. **Run the Application**: After building the application, you can run it using the following command:

    ```bash
    java -jar target/challenge-0.0.1-SNAPSHOT.jar
    ```

4. **Access the Application**: Once the application is up and running, you can access it in your web browser or using tools like cURL or Postman. The application will be available at `http://localhost:8080`.

## Endpoints

### Get Pokemon

```bash
curl http://localhost:8080/pokemon/{pokemon_name}
```

For example:

```bash
curl http://localhost:8080/pokemon/stunfisk
```

### Get Translated Pokemon

```bash
curl http://localhost:8080/pokemon/translated/{pokemon_name}
```

For example:

```bash
curl http://localhost:8080/pokemon/translated/stunfisk
```
