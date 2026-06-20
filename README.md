# Currency Converter API

A beginner-friendly Java Spring Boot project for converting currencies, checking exchange rates, listing supported currencies, and saving conversion history in an H2 in-memory database.

## Tech Stack

- Java 21
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
    │   ├── java/com/example/currencyconverter
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

## Run the Project

Requirements:

- Java 21
- Maven 3.9+

From the project folder:

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
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

```bash
curl -X POST http://localhost:8080/api/conversions \
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

```bash
curl http://localhost:8080/api/conversions/history \
  -H "X-API-KEY: demo-key-123"
```

### 3. Get Rates for Selected Currencies

```bash
curl "http://localhost:8080/api/rates?base=USD&targets=INR,AUD,EUR" \
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

```bash
curl http://localhost:8080/api/currencies \
  -H "X-API-KEY: demo-key-123"
```

## H2 Database Console

Open:

```text
http://localhost:8080/h2-console
```

Use these values:

```text
JDBC URL: jdbc:h2:mem:currencydb
User Name: sa
Password: leave blank
```

Query saved conversion history:

```sql
SELECT * FROM CONVERSION_HISTORY;
```

## Common Errors

### Missing API Key

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Missing or invalid API key"
}
```

### Invalid Currency Code

Currency codes must be 3-letter ISO codes such as `USD`, `INR`, `AUD`, or `EUR`.

## Build and Test

```bash
mvn clean test
mvn clean package
```

## Testing the API

Start the application from the project root:

```bash
mvn clean spring-boot:run
```

The API runs on:

```text
http://localhost:8082
```

Use the following API key in all secured requests:

```text
X-API-KEY: demo-key-123
```

## Postman Testing

Use the Postman desktop application when testing locally.

### Convert Currency

Method:

```text
POST
```

URL:

```text
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

Additional sample requests can be tested using the same endpoint.

```json
{
  "fromCurrency": "AUD",
  "toCurrency": "AED",
  "amount": 250
}
```

```json
{
  "fromCurrency": "CAD",
  "toCurrency": "AUD",
  "amount": 150
}
```

```json
{
  "fromCurrency": "AED",
  "toCurrency": "CAD",
  "amount": 500
}
```

### Get Conversion History

Method:

```text
GET
```

URL:

```text
http://localhost:8082/api/conversions/history
```

Header:

```text
X-API-KEY: demo-key-123
```

### Get Supported Currencies

Method:

```text
GET
```

URL:

```text
http://localhost:8082/api/currencies
```

Header:

```text
X-API-KEY: demo-key-123
```

### Get Exchange Rates

Method:

```text
GET
```

URL:

```text
http://localhost:8082/api/rates?base=USD&targets=AUD,AED,CAD
```

Header:

```text
X-API-KEY: demo-key-123
```

## PowerShell Testing

The API can also be tested directly from PowerShell.

### Convert USD to AUD

```powershell
curl.exe -X POST "http://localhost:8082/api/conversions" -H "X-API-KEY: demo-key-123" -H "Content-Type: application/json" -d "{\"fromCurrency\":\"USD\",\"toCurrency\":\"AUD\",\"amount\":100}"
```

### Convert AUD to AED

```powershell
curl.exe -X POST "http://localhost:8082/api/conversions" -H "X-API-KEY: demo-key-123" -H "Content-Type: application/json" -d "{\"fromCurrency\":\"AUD\",\"toCurrency\":\"AED\",\"amount\":250}"
```

### Convert CAD to AUD

```powershell
curl.exe -X POST "http://localhost:8082/api/conversions" -H "X-API-KEY: demo-key-123" -H "Content-Type: application/json" -d "{\"fromCurrency\":\"CAD\",\"toCurrency\":\"AUD\",\"amount\":150}"
```

### Convert AED to CAD

```powershell
curl.exe -X POST "http://localhost:8082/api/conversions" -H "X-API-KEY: demo-key-123" -H "Content-Type: application/json" -d "{\"fromCurrency\":\"AED\",\"toCurrency\":\"CAD\",\"amount\":500}"
```

### Get Conversion History

```powershell
curl.exe "http://localhost:8082/api/conversions/history" -H "X-API-KEY: demo-key-123"
```

### Get Supported Currencies

```powershell
curl.exe "http://localhost:8082/api/currencies" -H "X-API-KEY: demo-key-123"
```

### Get Exchange Rates

```powershell
curl.exe "http://localhost:8082/api/rates?base=USD&targets=AUD,AED,CAD" -H "X-API-KEY: demo-key-123"
```

## Running the Test Script

A PowerShell test script is available in the `scripts` folder.

From the project root, run:

```powershell
.\scripts\test-api.ps1
```

If script execution is blocked, enable script execution for the current PowerShell session only:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

Then run the script again:

```powershell
.\scripts\test-api.ps1
```

The direct `curl.exe` commands above can be used if the script is blocked by local PowerShell settings.

## H2 Database Verification

Open the H2 console in a browser:

```text
http://localhost:8082/h2-console
```

Use the following connection details:

```text
JDBC URL: jdbc:h2:mem:currencydb
User Name: sa
Password: 
```

Run this query to check saved conversion history:

```sql
SELECT * FROM CONVERSION_HISTORY ORDER BY CREATED_AT DESC;
```

To clear test data:

```sql
DELETE FROM CONVERSION_HISTORY;
```

## Invalid API Key Check

The API should reject requests with an invalid key.

```powershell
curl.exe "http://localhost:8082/api/currencies" -H "X-API-KEY: wrong-key"
```

