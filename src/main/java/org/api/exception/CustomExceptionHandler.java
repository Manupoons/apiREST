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
        Map<String, String> editionCompraErrors = new HashMap<>();
        editionCompraErrors.put("editionCompraError", ex.getMessage());
        return new ResponseEntity<>(editionCompraErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEventoException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEventoException(InvalidEventoException ex) {
        Map<String, String> eventoErrors = new HashMap<>();
        eventoErrors.put("eventoError", ex.getMessage());
        return new ResponseEntity<>(eventoErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEditedEventoException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEditedEventoException(InvalidEditedEventoException ex) {
        Map<String, String> editionEventoErrors = new HashMap<>();
        editionEventoErrors.put("editionEventoError", ex.getMessage());
        return new ResponseEntity<>(editionEventoErrors, HttpStatus.BAD_REQUEST);
    }
}