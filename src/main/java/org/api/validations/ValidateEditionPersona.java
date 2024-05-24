package org.api.validations;

import org.api.domain.Persona;
import org.api.exception.InvalidEditedPersonaException;


public class ValidateEditionPersona {

    public static void validateEditionPersona(Persona persona) {
        if(persona.getNombre_persona() != null){
            if (!persona.getNombre_persona().matches("^[a-zA-Z\\s]+$")) {
                throw new InvalidEditedPersonaException("The event name can only contain letters and spaces");
            }
        }
        if(persona.getCorreo_persona() != null){
            if (!persona.getCorreo_persona().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new InvalidEditedPersonaException("The email of the person is not valid");
            }
        }
        if(persona.getTelefono_persona() != null){
            if (!persona.getTelefono_persona().matches("^[0-9]{9}$")) {
                throw new InvalidEditedPersonaException("The phone number of the person is not valid");
            }
        }
    }
}
