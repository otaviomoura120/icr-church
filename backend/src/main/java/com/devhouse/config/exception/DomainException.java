package com.devhouse.config.exception;

public class DomainException extends NoStacktraceException {

    public DomainException(final String message) {
        super(message);
    }
}
