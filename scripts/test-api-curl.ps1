$BaseUrl = "http://localhost:8082"
$ApiKey = "demo-key-123"

curl.exe "$BaseUrl/api/currencies" -H "X-API-KEY: $ApiKey"

curl.exe "$BaseUrl/api/rates?base=USD&targets=AUD,AED,CAD" -H "X-API-KEY: $ApiKey"

curl.exe -X POST "$BaseUrl/api/conversions" -H "X-API-KEY: $ApiKey" -H "Content-Type: application/json" -d '{"fromCurrency":"USD","toCurrency":"AUD","amount":100}'

curl.exe -X POST "$BaseUrl/api/conversions" -H "X-API-KEY: $ApiKey" -H "Content-Type: application/json" -d '{"fromCurrency":"AUD","toCurrency":"AED","amount":250}'

curl.exe -X POST "$BaseUrl/api/conversions" -H "X-API-KEY: $ApiKey" -H "Content-Type: application/json" -d '{"fromCurrency":"CAD","toCurrency":"AUD","amount":500}'

curl.exe -X POST "$BaseUrl/api/conversions" -H "X-API-KEY: $ApiKey" -H "Content-Type: application/json" -d '{"fromCurrency":"AED","toCurrency":"CAD","amount":1000}'

curl.exe "$BaseUrl/api/conversions/history" -H "X-API-KEY: $ApiKey"
