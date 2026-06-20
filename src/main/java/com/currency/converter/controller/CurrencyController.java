package com.currency.converter.controller;

import com.currency.converter.config.ApiKeyAuthFilter;
import com.currency.converter.dto.ConversionRequest;
import com.currency.converter.dto.ConversionResponse;
import com.currency.converter.dto.RateResponse;
import com.currency.converter.service.CurrencyConversionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyConversionService currencyConversionService;

    public CurrencyController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @PostMapping("/conversions")
    @ResponseStatus(HttpStatus.CREATED)
    public ConversionResponse convert(@Valid @RequestBody ConversionRequest request,
                                      HttpServletRequest httpServletRequest) {
        String apiKeyOwner = (String) httpServletRequest.getAttribute(ApiKeyAuthFilter.API_KEY_OWNER_ATTRIBUTE);
        return currencyConversionService.convert(request, apiKeyOwner);
    }

    @GetMapping("/conversions/history")
    public List<ConversionResponse> history(HttpServletRequest httpServletRequest) {
        String apiKeyOwner = (String) httpServletRequest.getAttribute(ApiKeyAuthFilter.API_KEY_OWNER_ATTRIBUTE);
        return currencyConversionService.history(apiKeyOwner);
    }

    @GetMapping("/rates")
    public RateResponse rates(@RequestParam String base,
                              @RequestParam List<String> targets) {
        return currencyConversionService.rates(base, targets);
    }

    @GetMapping("/currencies")
    public Map<String, String> currencies() {
        return currencyConversionService.currencies();
    }
}
