package org.api.Mapper;

import org.api.domain.Compra;
import org.api.domain.CompraDTO;
import org.api.validations.ValidateCompra;
import org.api.validations.ValidateEditionCompra;
import org.springframework.stereotype.Component;

@Component
public class CompraMapper {

    public Compra compraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        compra.setEvento(compraDTO.getEvento());
        ValidateCompra.validateCompra(compra);
        return compra;
    }

    public static Compra editionCompraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        if (compraDTO.getNumero_entradas() != null){
            compra.setNumero_entradas(compraDTO.getNumero_entradas());
        }
        ValidateEditionCompra.validateEditionCompra(compra);
        return compra;
    }
}
