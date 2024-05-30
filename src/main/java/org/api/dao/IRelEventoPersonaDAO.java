package org.api.dao;

import org.api.domain.RelEventoPersona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRelEventoPersonaDAO extends CrudRepository<RelEventoPersona, Long> {
    List<RelEventoPersona> findByEventoIdEvento(Long idEvento);
    List<RelEventoPersona> findByPersonaIdPersona(Long idPersona);
    List<RelEventoPersona> findByEventoIdEventoAndPersonaIdPersona(Long idEvento, Long idPersona);
}
