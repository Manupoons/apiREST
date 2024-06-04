package org.api.domain;

import lombok.Data;
import org.api.dao.IPersonaDAO;

import java.util.Set;
import java.io.Serializable;

@Data
public class PersonaDTO implements Serializable {

    IPersonaDAO iPersonaDAO;

    private Long idPersona;

    private String nombre_persona;

    private String correo_persona;

    private String telefono_persona;

    private String fecha_baja;

    private Set<RelEventoPersona> relEventoPersonas;
}
