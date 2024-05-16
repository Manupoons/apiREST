package org.api.validations;

import org.api.domain.Compra;
import org.api.exception.InvalidEditedCompraException;

public class ValidateEditionCompra {
    public static void validateEditionCompra(Compra compra) {
        if(compra.getNumero_entradas()<1){
            throw new InvalidEditedCompraException("The number of edited tickets can't be less than 1");
        }
        if (compra.getNumero_entradas() > 20) {
            throw new InvalidEditedCompraException("The number of edited tickets can't be more than 20");
        }
    }
}
