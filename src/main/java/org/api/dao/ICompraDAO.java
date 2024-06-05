package org.api.dao;

import org.api.domain.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICompraDAO extends CrudRepository<Compra, Long> {
    Compra findByIdCompra(Long id);
    void deleteByIdCompra(Long id);
    List<Compra> findByIdEvento(Long idEvento);
    List<Compra> findByIdPersona(Long idPersona);
}
