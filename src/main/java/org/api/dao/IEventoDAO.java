package org.api.dao;

import org.api.domain.Evento;
import org.api.domain.IdValue;
import org.springframework.data.repository.CrudRepository;

public interface IEventoDAO extends CrudRepository<Evento, IdValue> {
    Evento findByIdEvento(IdValue id);
    void deleteByIdEvento(IdValue id);
}
