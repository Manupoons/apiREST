package org.api.validations;

import org.api.domain.Persona;
import org.api.exception.InvalidPersonaException;

public class ValidatePersona {

    public static void validatePersona(Persona persona) {
        if (persona.getNombre() == null) {
            throw new InvalidPersonaException("The persona name can't be empty");
        } else if (!persona.getNombre().matches("^[a-zA-Z\\s]+$")) {
            throw new InvalidPersonaException("The name of the person can only contain letters and spaces");
        }

        if (persona.getCorreo() == null) {
            throw new InvalidPersonaException("The persona email can't be empty");
        } else if (!persona.getCorreo().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidPersonaException("The email of the person is not valid");
        }

        if (persona.getTelefono_persona() != null){
            if (!persona.getTelefono_persona().matches("^[0-9]{9}$")) {
                throw new InvalidPersonaException("The phone number of the person is not valid. It must be exactly 9 digits long");
            }
        }
    }
}
