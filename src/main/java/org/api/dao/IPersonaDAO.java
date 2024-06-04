package org.api.dao;

import org.api.domain.IdValue;
import org.api.domain.Persona;
import org.springframework.data.repository.CrudRepository;

public interface IPersonaDAO extends CrudRepository<Persona, IdValue> {
    Persona findByIdPersona(IdValue id);
}
