package org.api.validations;

import org.api.domain.Persona;
import org.api.exception.InvalidPersonaException;

public class ValidatePersona {

    public static void validatePersona(Persona persona) {
        if (persona.getNombre_persona() == null) {
            throw new InvalidPersonaException("The persona name can't be empty");
        } else if (!persona.getNombre_persona().matches("^[a-zA-Z\\s]+$")) {
            throw new InvalidPersonaException("The name of the person can only contain letters and spaces");
        }

        if (persona.getCorreo_persona() == null) {
            throw new InvalidPersonaException("The persona email can't be empty");
        } else if (!persona.getCorreo_persona().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidPersonaException("The email of the person is not valid");
        }

        if (persona.getTelefono_persona() == null) {
            throw new InvalidPersonaException("The persona phone number can't be empty");
        } else if (!persona.getTelefono_persona().matches("^[0-9]{9}$")) {
            throw new InvalidPersonaException("The phone number of the person is not valid. It must be exactly 10 digits long");
        }
    }
}
