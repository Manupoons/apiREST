package org.api.servicio;

import org.api.domain.IdValue;
import org.api.domain.RelEventoPersona;

import java.util.List;

public interface IRelEventoPersonaService {

    List<RelEventoPersona> listarRelEventoPersonas();

    void createRelEventoPersona(IdValue idEvento, IdValue idPersona);
}
