package org.api.exception;

public class InvalidCompraException extends RuntimeException {
    public InvalidCompraException(String message) {
        super(message);
    }
}