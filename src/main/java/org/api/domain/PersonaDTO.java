package org.api.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class PersonaDTO implements Serializable {

    private Long idPersona;

    private String nombre_persona;

    private String correo_persona;

    private String telefono_persona;
}