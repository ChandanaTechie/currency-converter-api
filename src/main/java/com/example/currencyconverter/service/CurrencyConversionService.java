package com.example.currencyconverter.service;

import com.example.currencyconverter.dto.ConversionRequest;
import com.example.currencyconverter.dto.ConversionResponse;
import com.example.currencyconverter.dto.RateResponse;
import com.example.currencyconverter.entity.ConversionHistory;
import com.example.currencyconverter.exception.InvalidCurrencyException;
import com.example.currencyconverter.repository.ConversionHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private final FrankfurterClient frankfurterClient;
    private final ConversionHistoryRepository historyRepository;
    private final CurrencyValidator currencyValidator;

    public CurrencyConversionService(FrankfurterClient frankfurterClient,
                                     ConversionHistoryRepository historyRepository,
                                     CurrencyValidator currencyValidator) {
        this.frankfurterClient = frankfurterClient;
        this.historyRepository = historyRepository;
        this.currencyValidator = currencyValidator;
    }

    @Transactional
    public ConversionResponse convert(ConversionRequest request, String apiKeyOwner) {
        String fromCurrency = currencyValidator.normalize(request.fromCurrency());
        String toCurrency = currencyValidator.normalize(request.toCurrency());

        if (fromCurrency.equals(toCurrency)) {
            throw new InvalidCurrencyException("fromCurrency and toCurrency must be different");
        }

        BigDecimal rate = frankfurterClient.getRate(fromCurrency, toCurrency);
        BigDecimal convertedAmount = calculateConvertedAmount(request.amount(), rate);

        ConversionHistory history = new ConversionHistory();
        history.setApiKeyOwner(apiKeyOwner);
        history.setFromCurrency(fromCurrency);
        history.setToCurrency(toCurrency);
        history.setAmount(request.amount().setScale(4, RoundingMode.HALF_UP));
        history.setRate(rate.setScale(8, RoundingMode.HALF_UP));
        history.setConvertedAmount(convertedAmount);

        return toResponse(historyRepository.save(history));
    }

    @Transactional(readOnly = true)
    public List<ConversionResponse> history(String apiKeyOwner) {
        return historyRepository.findByApiKeyOwnerOrderByCreatedAtDesc(apiKeyOwner)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RateResponse rates(String baseCurrency, List<String> targetCurrencies) {
        String base = currencyValidator.normalize(baseCurrency);
        if (targetCurrencies == null || targetCurrencies.isEmpty()) {
            throw new InvalidCurrencyException("At least one target currency is required");
        }

        Map<String, BigDecimal> rates = new LinkedHashMap<>();
        for (String targetCurrency : targetCurrencies) {
            String target = currencyValidator.normalize(targetCurrency);
            if (!base.equals(target)) {
                rates.put(target, frankfurterClient.getRate(base, target));
            }
        }
        return new RateResponse(base, rates);
    }

    @Transactional(readOnly = true)
    public Map<String, String> currencies() {
        return frankfurterClient.getCurrencies();
    }

    public BigDecimal calculateConvertedAmount(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(4, RoundingMode.HALF_UP);
    }

    private ConversionResponse toResponse(ConversionHistory history) {
        return new ConversionResponse(
                history.getId(),
                history.getFromCurrency(),
                history.getToCurrency(),
                history.getAmount(),
                history.getRate(),
                history.getConvertedAmount(),
                history.getCreatedAt()
        );
    }
}
