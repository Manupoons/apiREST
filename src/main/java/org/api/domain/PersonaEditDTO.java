package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedPersonaException;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
public class PersonaEditDTO implements Serializable {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String nombre_persona;

    private String telefono_persona;

    private LocalDate fecha_baja;

    public static void validateEditionPersona(Persona persona) {
        if(persona.getNombre_persona() != null){
            if (!persona.getNombre_persona().matches("^[a-zA-Z\\s]+$")) {
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

