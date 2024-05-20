package org.api.domain;

import org.api.validations.ValidateCompra;
import org.api.validations.ValidateEditionCompra;

public class CompraMapper {

    public static Compra compraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        ValidateCompra.validateCompra(compra);
        return compra;
    }

    public static Compra editionCompraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        if (compraDTO.getNumero_entradas() != null){
            compra.setNumero_entradas(compraDTO.getNumero_entradas());
        }
        if(compraDTO.getFecha_compra() != null){
            compra.setFecha_compra(compraDTO.getFecha_compra());
        }
        ValidateEditionCompra.validateEditionCompra(compra);
        return compra;
    }
}
