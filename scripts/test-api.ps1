param(
    [string]$BaseUrl = "http://localhost:8082",
    [string]$ApiKey = "demo-key-123"
)

$commonHeaders = @{
    "X-API-KEY" = $ApiKey
}

$jsonHeaders = @{
    "X-API-KEY" = $ApiKey
    "Content-Type" = "application/json"
}

Write-Host "Checking supported currencies..."
Invoke-RestMethod -Uri "$BaseUrl/api/currencies" -Method Get -Headers $commonHeaders | Out-Host

Write-Host "Checking rates for AUD, AED, CAD and USD..."
Invoke-RestMethod -Uri "$BaseUrl/api/rates?base=USD&targets=AUD,AED,CAD" -Method Get -Headers $commonHeaders | Out-Host

$conversions = @(
    @{ fromCurrency = "USD"; toCurrency = "AUD"; amount = 100 },
    @{ fromCurrency = "AUD"; toCurrency = "AED"; amount = 250 },
    @{ fromCurrency = "CAD"; toCurrency = "AUD"; amount = 500 },
    @{ fromCurrency = "AED"; toCurrency = "CAD"; amount = 1000 }
)

foreach ($conversion in $conversions) {
    $body = $conversion | ConvertTo-Json
    Write-Host "Creating conversion: $($conversion.fromCurrency) to $($conversion.toCurrency)"
    Invoke-RestMethod -Uri "$BaseUrl/api/conversions" -Method Post -Headers $jsonHeaders -Body $body | Out-Host
}

Write-Host "Checking conversion history..."
Invoke-RestMethod -Uri "$BaseUrl/api/conversions/history" -Method Get -Headers $commonHeaders | Out-Host
