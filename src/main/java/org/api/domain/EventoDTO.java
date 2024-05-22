package org.api.domain;

import lombok.*;
import java.io.Serializable;
import javax.validation.constraints.*;

@Data
public class EventoDTO implements Serializable {
    private Long id_evento;

    private String nombre_evento;

    private String hora_evento;

    private String fecha_evento;

    private String empresa_evento;
}
