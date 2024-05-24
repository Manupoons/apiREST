package org.api.dao;

import org.api.domain.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPersonaDAO extends CrudRepository<Persona, Long> {
    List<Persona> findByEventoIdEvento(Long idEvento);
}
