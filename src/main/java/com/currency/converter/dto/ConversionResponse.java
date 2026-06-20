package com.currency.converter.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ConversionResponse(
        Long id,
        String fromCurrency,
        String toCurrency,
        BigDecimal amount,
        BigDecimal rate,
        BigDecimal convertedAmount,
        Instant createdAt
) {
}
