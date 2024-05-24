package org.api.validations;

import java.time.format.*;
import java.time.LocalDate;
import org.api.domain.Compra;
import org.api.exception.InvalidCompraException;

public class ValidateCompra {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateCompra(Compra compra) {
        if(compra.getNombre_cliente() != null){
            if (compra.getNombre_cliente().length() > 50) {
                throw new InvalidCompraException("The client name can't be longer than 50 characters");
            }
            if (!compra.getNombre_cliente().matches("^[a-zA-Z\\s]+$")) {
                throw new InvalidCompraException("The client name can only contain letters and spaces");
            }
        }
        if (compra.getNombre_cliente() == null) {
            throw new InvalidCompraException("The client name can't be null");
        }
        if (compra.getNumero_entradas() == null) {
            throw new InvalidCompraException("The number of tickets can't be null");
        }
        if (compra.getNumero_entradas() != (double) compra.getNumero_entradas()) {
            throw new InvalidCompraException("The number of tickets must be an integer");
        }
        if (compra.getNumero_entradas() < 1){
            throw new InvalidCompraException("The number of tickets can't be less than 1");
        }
        if (compra.getNumero_entradas() > 20) {
            throw new InvalidCompraException("The number of tickets can't be more than 20");
        }
        if (compra.getFecha_compra() == null) {
            throw new InvalidCompraException("The purchase date can't be null");
        }
        if (!isValidDate(compra.getFecha_compra())) {
            throw new InvalidCompraException("Invalid date format. Expected format is yyyy-MM-dd");
        }
        if (LocalDate.parse(compra.getFecha_compra(), DATE_FORMATTER).isAfter(LocalDate.now())) {
            throw new InvalidCompraException("The purchase date can't be in the future");
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
