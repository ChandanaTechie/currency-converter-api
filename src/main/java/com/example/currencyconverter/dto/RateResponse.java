package com.example.currencyconverter.dto;

import java.math.BigDecimal;
import java.util.Map;

public record RateResponse(
        String baseCurrency,
        Map<String, BigDecimal> rates
) {
}
