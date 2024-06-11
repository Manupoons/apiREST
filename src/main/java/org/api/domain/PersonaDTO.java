package org.api.domain;

import lombok.Data;
import org.api.dao.IPersonaDAO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.io.Serializable;

@Data
public class PersonaDTO implements Serializable {

    IPersonaDAO iPersonaDAO;

    private Long idPersona;

    private String nombre_persona;

    private String correo_persona;

    private String telefono_persona;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_baja;

    private Set<RelEventoPersona> relEventoPersonas;
}
