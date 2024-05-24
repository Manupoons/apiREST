package org.api.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

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

    @JsonProperty(required = true)
    private Long idEvento;

    @JsonProperty(required = true)
    private Long idPersona;
}
