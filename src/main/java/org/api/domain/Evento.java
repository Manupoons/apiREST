package org.api.domain;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

@Entity
@Data
@Table(name = "evento")
public class Evento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    private String nombre_evento;

    @JsonProperty(required = true)
    private String hora_evento;

    @JsonProperty(required = true)
    private String fecha_evento;

    @JsonProperty(required = true)
    private String empresa_evento;

    @OneToMany(mappedBy = "evento")
    private Set<RelEventoPersona> relEventoPersona;
}
