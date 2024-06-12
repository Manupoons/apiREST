package org.api.validations;

import org.api.exception.InvalidEditedCompraException;

public class ValidateEditionCompra {

    public static void validateEditionCompra(int numero_entradas) {
        if (numero_entradas <= 1 || numero_entradas >= 20) {
            throw new InvalidEditedCompraException("The number of edited tickets has to be between 1 and 20");
        }
    }
}
