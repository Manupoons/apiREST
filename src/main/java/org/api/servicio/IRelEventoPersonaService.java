package org.api.servicio;

import org.api.domain.Evento;
import org.api.domain.IdValue;
import org.api.domain.Persona;
import org.api.domain.RelEventoPersona;

import java.util.List;

public interface IRelEventoPersonaService {

    List<RelEventoPersona> listarRelEventoPersonas();

    void createRelEventoPersona(Evento evento, Persona persona);

    void eliminarRelEventoPersona(IdValue idRelEventoPersona);
}
