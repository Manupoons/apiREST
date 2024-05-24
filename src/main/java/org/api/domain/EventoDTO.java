package org.api.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class EventoDTO implements Serializable {
    private Long idEvento;

    private String nombre_evento;

    private String hora_evento;

    private String fecha_evento;

    private String empresa_evento;
}
