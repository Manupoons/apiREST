package org.api.validations;

import org.api.domain.Compra;
import org.api.exception.InvalidCompraException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidateCompraConPersona {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateCompraconPersona(Compra compra) {

        if (compra.getNombre_cliente() == null) {
            throw new InvalidCompraException("The client name can't be null");
        } else {
            if (compra.getNombre_cliente().length() > 50) {
                throw new InvalidCompraException("The client name can't be longer than 50 characters");
            }
            if (!compra.getNombre_cliente().matches("^[a-zA-Z\\s]+$")) {
                throw new InvalidCompraException("The client name can only contain letters and spaces");
            }
        }

        if (compra.getNumero_entradas() == null) {
            throw new InvalidCompraException("The number of tickets can't be null");
        } else {
            if (compra.getNumero_entradas() < 1 || compra.getNumero_entradas() > 20){
                throw new InvalidCompraException("The number of tickets has to be between 1 and 20");
            }
        }

        if (compra.getFecha_compra() == null) {
            throw new InvalidCompraException("The purchase date can't be null");
        } else {
            if (!isValidDate(compra.getFecha_compra())) {
                throw new InvalidCompraException("Invalid date format. Expected format is yyyy-MM-dd");
            }
            if (LocalDate.parse(compra.getFecha_compra(), DATE_FORMATTER).isAfter(LocalDate.now())) {
                throw new InvalidCompraException("The purchase date can't be in the future");
            }
        }

        if (compra.getIdEvento() == null) {
            throw new InvalidCompraException("The evento id can't be null");
        }
        if (compra.getIdPersona() == null) {
            throw new InvalidCompraException("The persona id can't be null");
        }
    }

    private static boolean isValidDate(String date){
        try{
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        }catch (DateTimeParseException e){
            return false;
        }
    }
}
