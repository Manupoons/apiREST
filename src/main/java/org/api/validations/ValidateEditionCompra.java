package org.api.validations;

import java.time.format.*;
import java.time.LocalDate;
import org.api.domain.Compra;
import org.api.exception.InvalidEditedCompraException;

public class ValidateEditionCompra {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateEditionCompra(Compra compra) {
        if (compra.getNumero_entradas() != null) {
            if (compra.getNumero_entradas() != (double) compra.getNumero_entradas()) {
                throw new InvalidEditedCompraException("The number of tickets must be an integer");
            }
            if(compra.getNumero_entradas()<1){
                throw new InvalidEditedCompraException("The number of edited tickets can't be less than 1");
            }
            if (compra.getNumero_entradas() > 20) {
                throw new InvalidEditedCompraException("The number of edited tickets can't be more than 20");
            }
        }
        if(compra.getFecha_compra() != null){
            if (!isValidDate(compra.getFecha_compra())){
                throw new InvalidEditedCompraException("Invalid date format. Expected format is yyyy-MM-dd");
            }
            if (LocalDate.parse(compra.getFecha_compra(), DATE_FORMATTER).isAfter(LocalDate.now())) {
                throw new InvalidEditedCompraException("The purchase date can't be in the future");
            }
        }
        if (compra.getEvento() != null){
            if (compra.getEvento().getIdEvento() != (double) compra.getEvento().getIdEvento()) {
                throw new InvalidEditedCompraException("The id of the event must be an integer");
            }
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
