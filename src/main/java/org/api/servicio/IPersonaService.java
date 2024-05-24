package org.api.servicio;

import org.api.domain.Persona;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPersonaService {

    List<Persona> listarPersonas();

    ResponseEntity<Persona> nuevaPersona(Persona persona);

    ResponseEntity<Persona> editarPersona(Long id, Persona persona);

    void eliminarPersona(Long id);
}
