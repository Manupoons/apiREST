package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedPersonaException;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Data
public class PersonaEditDTO implements Serializable {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String nombre;

    private String telefono_persona;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fecha_baja;

    public static void validateEditionPersona(Persona persona) {
        if(persona.getNombre() != null){
            if (!persona.getNombre().matches("^[a-zA-Z\\s]+$")) {
                throw new InvalidEditedPersonaException("The persona name can only contain letters and spaces");
            }
        }

        if(persona.getTelefono_persona() != null){
            if (!persona.getTelefono_persona().matches("^[0-9]{9}$")) {
                throw new InvalidEditedPersonaException("The phone number of the person is not valid");
            }
        }
    }
}

