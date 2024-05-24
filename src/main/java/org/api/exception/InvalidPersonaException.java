package org.api.exception;

public class InvalidPersonaException extends RuntimeException {
    public InvalidPersonaException(String message) {
        super(message);
    }
}