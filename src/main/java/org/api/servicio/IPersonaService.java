package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.IdValue;
import org.api.domain.Persona;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPersonaService {

    List<Persona> listarPersonas();

    ResponseEntity<Persona> nuevaPersona(Persona persona);

    ResponseEntity<Persona> editarPersona(IdValue id, Persona persona);

    List<Compra> listadoCompraPorPersona(IdValue id);

    //List<Persona> listadoPersonasPorEvento(IdValue id);

    void eliminarPersona(IdValue id);
}
