package org.api.exception;

import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidEventoException.class)
    public String handleInvalidEvento(InvalidEventoException ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "Errores/Errores";
    }

    @ExceptionHandler(InvalidEditedEventoException.class)
    public String handleInvalidEditedEventoException(InvalidEditedEventoException ex, Model model) {
        model.addAttribute("errorEdition", ex.getMessage());
        return "Errores/ErroresEditar";
    }

    @ExceptionHandler(InvalidPersonaException.class)
    public String handleInvalidPersona(InvalidPersonaException ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "Errores/Errores";
    }

    @ExceptionHandler(InvalidEditedPersonaException.class)
    public String handleInvalidEditedPersonaException(InvalidEditedPersonaException ex, Model model) {
        model.addAttribute("errorEdition", ex.getMessage());
        return "Errores/ErroresEditar";
    }

//    @ExceptionHandler(InvalidCompraException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidCompraException(InvalidCompraException ex) {
//        Map<String, String> compraErrors = new HashMap<>();
//        compraErrors.put("compraError", ex.getMessage());
//        return new ResponseEntity<>(compraErrors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidEditedCompraException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidEditedCompraException(InvalidEditedCompraException ex) {
//        Map<String, String> editionCompraErrors = new HashMap<>();
//        editionCompraErrors.put("editionCompraError", ex.getMessage());
//        return new ResponseEntity<>(editionCompraErrors, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(InvalidEventoException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidEventoException(InvalidEventoException ex) {
//        Map<String, String> eventoErrors = new HashMap<>();
//        eventoErrors.put("eventoError", ex.getMessage());
//        return new ResponseEntity<>(eventoErrors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidEditedEventoException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidEditedEventoException(InvalidEditedEventoException ex) {
//        Map<String, String> editionEventoErrors = new HashMap<>();
//        editionEventoErrors.put("editionEventoError", ex.getMessage());
//        return new ResponseEntity<>(editionEventoErrors, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(InvalidPersonaException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidPersonaException(InvalidPersonaException ex) {
//        Map<String, String> personaErrors = new HashMap<>();
//        personaErrors.put("personaError", ex.getMessage());
//        return new ResponseEntity<>(personaErrors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidEditedPersonaException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidEditedPersonaException(InvalidEditedPersonaException ex) {
//        Map<String, String> editionPersonaErrors = new HashMap<>();
//        editionPersonaErrors.put("editionPersonaError", ex.getMessage());
//        return new ResponseEntity<>(editionPersonaErrors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidURLException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidURLException(InvalidURLException ex) {
//        Map<String, String> urlErrors = new HashMap<>();
//        urlErrors.put("urlErrors", ex.getMessage());
//        return new ResponseEntity<>(urlErrors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidIdException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidIdException(InvalidIdException ex) {
//        Map<String, String> idErrors = new HashMap<>();
//        idErrors.put("idError", ex.getMessage());
//        return new ResponseEntity<>(idErrors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidRelEventoPersona.class)
//    public ResponseEntity<Map<String, String>> handleInvalidRelEventoPersona(InvalidRelEventoPersona ex) {
//        Map<String, String> relEventoPersonaErrors = new HashMap<>();
//        relEventoPersonaErrors.put("relEventoPersonaError", ex.getMessage());
//        return new ResponseEntity<>(relEventoPersonaErrors, HttpStatus.BAD_REQUEST);
//    }
}