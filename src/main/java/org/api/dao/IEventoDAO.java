package org.api.dao;

import org.api.domain.Evento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEventoDAO extends CrudRepository<Evento, Long> {
    List<Evento> findByPersonaIdPersona(Long idPersona);
}
