package org.api.dao;

import org.api.domain.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICompraDAO extends CrudRepository<Compra, Long> {
    List<Compra> findByEventoIdEvento(Long idEvento);
}
