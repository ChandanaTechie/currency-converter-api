package com.example.currencyconverter.exception;

public class ExternalRateException extends RuntimeException {

    public ExternalRateException(String message) {
        super(message);
    }

    public ExternalRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
