package org.api.dao;

import org.api.domain.Evento;
import org.api.domain.Persona;
import org.api.domain.RelEventoPersona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRelEventoPersonaDAO extends CrudRepository<RelEventoPersona, Long> {
    List<Evento> findByPersonaIdPersona(Long idPersona);
    List<Persona> findByEventoIdEvento(Long idEvento);
}
