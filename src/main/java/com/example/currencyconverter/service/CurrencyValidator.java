package com.example.currencyconverter.service;

import com.example.currencyconverter.exception.InvalidCurrencyException;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.regex.Pattern;

@Component
public class CurrencyValidator {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");

    public String normalize(String currencyCode) {
        String normalized = currencyCode == null ? "" : currencyCode.trim().toUpperCase(Locale.ROOT);
        if (!CODE_PATTERN.matcher(normalized).matches()) {
            throw new InvalidCurrencyException("Currency code must be a 3-letter ISO code");
        }
        return normalized;
    }
}
