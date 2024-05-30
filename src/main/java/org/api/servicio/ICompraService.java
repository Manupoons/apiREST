package org.api.servicio;

import org.api.domain.Compra;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompraService {

    List<Compra> listarCompras();

    ResponseEntity<Compra> nuevaCompra(Compra compra);

    void createRelEventoPersona(Long idEvento, Long idPersona);

    ResponseEntity<Compra> editarCompra(Long id, Compra compra);

    void eliminarCompra(Long id);
}
