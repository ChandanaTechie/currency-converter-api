package com.example.currencyconverter.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyConversionServiceTest {

    @Test
    void shouldCalculateConvertedAmountWithFourDecimalPlaces() {
        CurrencyConversionService service = new CurrencyConversionService(null, null, null);

        BigDecimal result = service.calculateConvertedAmount(new BigDecimal("100.00"), new BigDecimal("83.456789"));

        assertThat(result).isEqualByComparingTo(new BigDecimal("8345.6789"));
    }
}
