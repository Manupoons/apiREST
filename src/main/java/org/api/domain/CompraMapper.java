package org.api.domain;

public class CompraMapper {
    public static CompraDTO compraToCompraDTO(Compra compra) {
        CompraDTO compraDTO = new CompraDTO();
        compra.setNombre_cliente(compra.getNombre_cliente());
        compra.setNumero_entradas(compra.getNumero_entradas());
        compra.setFecha_compra(compra.getFecha_compra());
        return compraDTO;
    }

    public static Compra compraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        return compra;
    }
}
