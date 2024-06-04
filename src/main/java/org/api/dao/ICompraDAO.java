package org.api.dao;

import org.api.domain.Compra;
import org.api.domain.IdValue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICompraDAO extends CrudRepository<Compra, IdValue> {
    Compra findByIdCompra(IdValue id);
    void deleteByIdCompra(IdValue id);
    List<Compra> findByIdEvento(IdValue idEvento);
    List<Compra> findByIdPersona(IdValue idPersona);
}
