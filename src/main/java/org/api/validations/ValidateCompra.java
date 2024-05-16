package org.api.validations;

import org.api.domain.Compra;
import org.api.exception.InvalidCompraException;

public class ValidateCompra {
    public static void validateCompra(Compra compra) {
        if (compra.getNumero_entradas() == null) {
            throw new InvalidCompraException("The number of tickets can't be null");
        }
        if(compra.getNumero_entradas()<1){
            throw new InvalidCompraException("The number of tickets can't be less than 1");
        }
        if (compra.getNumero_entradas() > 20) {
            throw new InvalidCompraException("The number of tickets can't be more than 20");
        }
    }
}
