package org.api.dao;

import org.api.domain.Compra;
import org.springframework.data.repository.CrudRepository;

public interface ICompraDAO extends CrudRepository<Compra, Long> {
}
