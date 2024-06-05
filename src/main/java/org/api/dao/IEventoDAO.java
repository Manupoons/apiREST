package org.api.dao;

import org.api.domain.Evento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEventoDAO extends CrudRepository<Evento, Long> {
    Evento findByIdEvento(Long id);
    void deleteByIdEvento(Long id);
    List<Evento> findAllById(Iterable<Long> idsEventos);
}
