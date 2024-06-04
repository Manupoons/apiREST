package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.IdValue;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompraService {

    List<Compra> listarCompras();

    ResponseEntity<Compra> nuevaCompra(Compra compra, IdValue idPersona);

   // void createRelEventoPersona(Long idEvento, Long idPersona);

    Compra editarCompra(IdValue id, int numero_entradas);

    void eliminarCompra(IdValue id);
}
