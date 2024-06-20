package org.api.dao;

import org.api.domain.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPersonaDAO extends CrudRepository<Persona, Long> {
    Persona findByIdPersona(Long idPersona);
    Persona findByCorreo(String nombre);
    List<Persona> findAllById(Iterable<Long> idsPersona);
    void deleteByIdPersona(Long id);
}
