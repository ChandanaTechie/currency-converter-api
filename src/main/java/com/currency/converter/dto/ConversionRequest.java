package com.currency.converter.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConversionRequest(
        @NotBlank(message = "fromCurrency is required")
        String fromCurrency,

        @NotBlank(message = "toCurrency is required")
        String toCurrency,

        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be greater than zero")
        BigDecimal amount
) {
}
