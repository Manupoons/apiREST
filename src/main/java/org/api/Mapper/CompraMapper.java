package org.api.Mapper;

import org.api.domain.Compra;
import org.api.domain.CompraDTO;
import org.api.validations.ValidateCompra;
import org.api.validations.ValidateEditionCompra;

public class CompraMapper {

    public static Compra compraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        compra.setId_evento(compraDTO.getId_evento());
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
        if (compraDTO.getId_evento() != null){
            compra.setId_evento(compraDTO.getId_evento());
        }
        ValidateEditionCompra.validateEditionCompra(compra);
        return compra;
    }
}
