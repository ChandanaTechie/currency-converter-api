# Currency Converter API

A beginner-friendly Java Spring Boot project for converting currencies, checking exchange rates, listing supported currencies, and saving conversion history in an H2 in-memory database.

## Tech Stack

- Java 25
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- Jackson JSON processing
- RestClient for external API calls

## Features

- API key based access using the `X-API-KEY` request header
- Currency conversion between two currencies
- Conversion history saved per API key owner
- Supported currency list
- Exchange rates for selected target currencies
- Centralized exception handling
- H2 database console for checking saved history

## Project Structure

```text
currency-converter-api
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/currency/converter
    │   │   ├── config
    │   │   ├── controller
    │   │   ├── dto
    │   │   ├── entity
    │   │   ├── exception
    │   │   ├── repository
    │   │   └── service
    │   └── resources/application.yml
    └── test
```

## Testing the API

```text
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8082
```

## Demo API Keys

Use one of these API keys in the request header:

```text
X-API-KEY: demo-key-123
X-API-KEY: student-key-456
```

API keys are configured in:

```text
src/main/resources/application.yml
```

## API Endpoints
### 1. Convert Currency

```text

curl -X POST http://localhost:8082/api/conversions \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: demo-key-123" \
  -d '{
    "fromCurrency": "USD",
    "toCurrency": "INR",
    "amount": 100
  }'
```

Sample response:

```json
{
  "id": 1,
  "fromCurrency": "USD",
  "toCurrency": "INR",
  "amount": 100.0000,
  "rate": 83.45670000,
  "convertedAmount": 8345.6700,
  "createdAt": "2026-06-20T06:00:00Z"
}
```

### 2. Get Conversion History

```text
 
 curl http://localhost:8082/api/conversions/history \
  -H "X-API-KEY: demo-key-123"
```

### 3. Get Rates for Selected Currencies

```text
curl "http://localhost:8082/api/rates?base=USD&targets=INR,AUD,EUR" \
  -H "X-API-KEY: demo-key-123"
```

Sample response:

```json
{
  "baseCurrency": "USD",
  "rates": {
    "INR": 83.4567,
    "AUD": 1.5321,
    "EUR": 0.9234
  }
}
```

### 4. List Supported Currencies

```text
curl http://localhost:8082/api/currencies \
  -H "X-API-KEY: demo-key-123"
```


## Postman Testing

Use the Postman desktop application when testing locally.

Method:

```text
POST URL:
http://localhost:8082/api/conversions
```

Headers:

```text
X-API-KEY: demo-key-123
Content-Type: application/json
```

Body:

```json
{
  "fromCurrency": "USD",
  "toCurrency": "AUD",
  "amount": 100
}
```

