package org.api.domain;

import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "evento")
public class Evento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @NotNull(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La hora no puede estar vacía")
    private String hora_evento;

    @NotNull(message = "La fecha no puede estar vacía")
    private String fecha_evento;

    @NotNull(message = "El nombre de la empresa no puede estar vacío")
    private String empresa_evento;

    @OneToMany(mappedBy = "evento")
    private Set<RelEventoPersona> relEventoPersona;
}
