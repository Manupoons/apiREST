package org.api.Mapper;

import org.api.dao.IEventoDAO;
import org.api.domain.Compra;
import org.api.domain.CompraDTO;
import org.api.domain.Evento;
import org.api.validations.ValidateCompra;
import org.api.validations.ValidateEditionCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompraMapper {

    private final IEventoDAO iEventoDAO;

    @Autowired
    public CompraMapper(IEventoDAO iEventoDAO) {
        this.iEventoDAO = iEventoDAO;
    }

    public Compra compraDTOToCompra(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setNombre_cliente(compraDTO.getNombre_cliente());
        compra.setNumero_entradas(compraDTO.getNumero_entradas());
        compra.setFecha_compra(compraDTO.getFecha_compra());
        Long idEvento = compraDTO.getEvento().getIdEvento();
        Evento evento = iEventoDAO.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento not found with id: " + idEvento));

        compra.setEvento(evento);
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
