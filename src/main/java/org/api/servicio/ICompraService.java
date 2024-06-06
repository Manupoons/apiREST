package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.IdValue;

import java.util.List;

public interface ICompraService {

    List<Compra> listarCompras();

    Compra nuevaCompra(Compra compra);

    Compra nuevaCompraConPersona(Compra compra, IdValue idPersona);

    Compra editarCompra(IdValue id, int numero_entradas);

    void eliminarCompra(IdValue id);
}
