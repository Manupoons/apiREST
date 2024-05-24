package org.api.servicio;

import org.api.domain.Evento;
import org.api.domain.Persona;

import java.util.List;

public interface IRelEventoPersonaService {

    List<Evento> listadoEventosPorPersona(Long idPersona);

    List<Persona> listadoPersonaPorEvento(Long idEvento);
}
