package org.api.exception;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCompraException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCompraException(InvalidCompraException ex) {
        Map<String, String> compraErrors = new HashMap<>();
        compraErrors.put("compraError", ex.getMessage());
        return new ResponseEntity<>(compraErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEditedCompraException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEditedCompraException(InvalidEditedCompraException ex) {
        Map<String, String> editionErrors = new HashMap<>();
        editionErrors.put("editionError", ex.getMessage());
        return new ResponseEntity<>(editionErrors, HttpStatus.BAD_REQUEST);
    }
}