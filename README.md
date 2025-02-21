# TrueLayer Challenge

Pokedex with fun translations.

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
    java -jar target/truelayer-challenge-0.0.1-SNAPSHOT.jar
    ```

4. **Access the Application**: Once the application is up and running, you can access it in your web browser or using tools like cURL or Postman. The application will be available at `http://localhost:8080`.


## Endpoints

### Get Product

Get a product by ID:

```bash
curl http://localhost:8080/products/{product_id}
```

For example:

```bash
curl http://localhost:8080/products/1
```

Example response:

```json
{
   "id": 1,
   "name": "Smartphone",
   "price": 500.00,
   "quantity": 10
}
```

Example not found error response:

```json
{
   "status": 404,
   "error": "Product not found with ID: 1"
}
```

### Post Product

Create a product:

```bash
curl -X POST http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -d 'create_body'
```

For example:

```bash
curl -X POST http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Smartphone", "price": 500.0, "quantity": 10}'
```

Example validation error response:

```json
{
   "status": 400,
   "error": "Validation error",
   "messages": [
      "Invalid -1 value for price",
      "Invalid null value for quantity"
   ]
}
```

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
