package org.api.domain;


import lombok.Data;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "evento_persona_relacion")
public class RelEventoPersona implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEventoPersona;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;
}
