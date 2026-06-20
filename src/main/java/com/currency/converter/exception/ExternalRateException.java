package com.currency.converter.exception;

public class ExternalRateException extends RuntimeException {

    public ExternalRateException(String message) {
        super(message);
    }

    public ExternalRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
