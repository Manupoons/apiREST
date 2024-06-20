package org.api.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;
import java.io.Serializable;

@Data
public class EventoDTO implements Serializable {

    private Long idEvento;

    private String nombre;

    private String hora_evento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fecha_evento;

    private String empresa_evento;

    private Set<RelEventoPersona> relEventoPersonas;
}
