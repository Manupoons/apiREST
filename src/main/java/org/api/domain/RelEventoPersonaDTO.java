package org.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RelEventoPersonaDTO implements Serializable {

    private Long idEventoPersona;

    private Long idEvento;

    private Long idPersona;
}
