package org.api.domain;

import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    private String hora_evento;

    private Date fecha_evento;

    private String empresa_evento;

    @OneToMany(mappedBy = "evento")
    private Set<RelEventoPersona> relEventoPersona;
}
