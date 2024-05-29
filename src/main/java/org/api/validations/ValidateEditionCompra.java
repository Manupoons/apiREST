package org.api.validations;

import org.api.domain.Compra;
import org.api.exception.InvalidEditedCompraException;

public class ValidateEditionCompra {


    public static void validateEditionCompra(Compra compra) {
        if (compra.getNumero_entradas() != null) {
            if(compra.getNumero_entradas()<1 || compra.getNumero_entradas() > 20){
                throw new InvalidEditedCompraException("The number of edited tickets has to be between 1 and 20");
            }
        }
    }
}
