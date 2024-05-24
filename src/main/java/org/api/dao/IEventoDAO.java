package org.api.dao;

import org.api.domain.Evento;
import org.springframework.data.repository.CrudRepository;

public interface IEventoDAO extends CrudRepository<Evento, Long> {
}
