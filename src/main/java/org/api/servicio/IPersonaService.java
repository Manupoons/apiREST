package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.Persona;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPersonaService {

    List<Persona> listarPersonas();

    ResponseEntity<Persona> nuevaPersona(Persona persona);

    ResponseEntity<Persona> editarPersona(Long id, Persona persona);

    List<Compra> listadoCompraPorPersona(Long id);

    List<Persona> listadoPersonasPorEvento(Long id);

    void eliminarPersona(Long id);
}
