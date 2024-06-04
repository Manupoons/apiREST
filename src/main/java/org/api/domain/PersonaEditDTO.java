package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedPersonaException;

import java.io.Serializable;

@Data
public class PersonaEditDTO implements Serializable {

    private String nombre_persona;

    private String telefono_persona;

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
