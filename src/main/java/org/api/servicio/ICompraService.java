package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.IdValue;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompraService {

    List<Compra> listarCompras();

    ResponseEntity<Compra> nuevaCompra(Compra compra);

    ResponseEntity<Compra> nuevaCompraConPersona(Compra compra, IdValue idPersona);

    Compra editarCompra(IdValue id, int numero_entradas);

    void eliminarCompra(IdValue id);
}
