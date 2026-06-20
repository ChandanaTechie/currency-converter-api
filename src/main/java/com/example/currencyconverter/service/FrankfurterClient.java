package com.example.currencyconverter.service;

import com.example.currencyconverter.exception.ExternalRateException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Component
public class FrankfurterClient {

    private final RestClient frankfurterRestClient;

    public FrankfurterClient(RestClient frankfurterRestClient) {
        this.frankfurterRestClient = frankfurterRestClient;
    }

    public BigDecimal getRate(String fromCurrency, String toCurrency) {
        try {
            JsonNode response = frankfurterRestClient.get()
                    .uri("/v2/rate/{fromCurrency}/{toCurrency}", fromCurrency, toCurrency)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JsonNode.class);

            if (response == null || response.get("rate") == null || response.get("rate").isNull()) {
                throw new ExternalRateException("Rate was not available for the requested currency pair");
            }

            return response.get("rate").decimalValue();
        } catch (RestClientException exception) {
            throw new ExternalRateException("Unable to fetch exchange rate", exception);
        }
    }

    public Map<String, String> getCurrencies() {
        try {
            JsonNode response = frankfurterRestClient.get()
                    .uri("/v2/currencies")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JsonNode.class);

            if (response == null || !response.isObject()) {
                throw new ExternalRateException("Currency list was not available");
            }

            Map<String, String> currencies = new TreeMap<>();
            Iterator<Map.Entry<String, JsonNode>> fields = response.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                currencies.put(field.getKey(), displayName(field.getValue()));
            }
            return currencies;
        } catch (RestClientException exception) {
            throw new ExternalRateException("Unable to fetch supported currencies", exception);
        }
    }

    private String displayName(JsonNode value) {
        if (value == null || value.isNull()) {
            return "";
        }
        if (value.isTextual()) {
            return value.asText();
        }
        if (value.has("name")) {
            return value.get("name").asText();
        }
        return value.toString();
    }
}
