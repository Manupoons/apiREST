package org.api.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.io.Serializable;

@Data
public class EventoDTO implements Serializable {

    private Long idEvento;

    @NotNull(message = "El nombre no puede estar vacío")
    private String nombre;

    private String hora_evento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_evento;

    private String empresa_evento;

    private Set<RelEventoPersona> relEventoPersonas;
}
