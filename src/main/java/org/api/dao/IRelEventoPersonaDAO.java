package org.api.dao;

import org.api.domain.IdValue;
import org.api.domain.RelEventoPersona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRelEventoPersonaDAO extends CrudRepository<RelEventoPersona, Long> {
    RelEventoPersona findByIdEventoPersona(IdValue id);
    List<RelEventoPersona> findByEventoIdEvento(IdValue idEvento);
    List<RelEventoPersona> findByPersonaIdPersona(IdValue idPersona);
    List<RelEventoPersona> findByEventoIdEventoAndPersonaIdPersona(IdValue idEvento, IdValue idPersona);
}
