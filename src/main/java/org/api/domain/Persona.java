package org.api.domain;


import lombok.Data;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Set;
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
    private Long idPersona;

    private String nombre_persona;

    private String correo_persona;

    private String telefono_persona;

    private LocalDate fecha_baja;

    @OneToMany(mappedBy = "persona")
    private Set<RelEventoPersona> relEventoPersona;
}
