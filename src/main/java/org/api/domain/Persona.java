package org.api.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name="personarpp")
public class Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    @JsonProperty(required = true)
    private String nombre_persona;

    @JsonProperty(required = true)
    private String correo_persona;

    @JsonProperty(required = true)
    private String telefono_persona;
}
