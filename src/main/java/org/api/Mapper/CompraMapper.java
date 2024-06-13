package org.api.Mapper;

import org.api.domain.*;
import org.api.validations.ValidateCompra;
import org.springframework.stereotype.Component;

@Component
public class CompraMapper {

    public Compra compraDTOToCompra(CompraDTO compraDTO, IdValue idEvento) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        compra.setIdEvento(idEvento.getValue());
        ValidateCompra.validateCompra(compra);
        return compra;
    }

    public Compra compraDTOToCompraConPersona(CompraDTO compraDTO, IdValue idEvento, IdValue idPersona) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        compra.setIdEvento(idEvento.getValue());
        compra.setIdPersona(idPersona.getValue());
        ValidateCompra.validateCompra(compra);
        return compra;
    }
}
