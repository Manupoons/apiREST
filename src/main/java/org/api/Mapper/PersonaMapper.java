package org.api.Mapper;

import org.api.domain.Persona;
import org.api.domain.PersonaDTO;
import org.api.validations.ValidatePersona;
import org.api.validations.ValidateEditionPersona;

public class PersonaMapper {

    public static Persona personaDTOToPersona(PersonaDTO personaDTO) {
        Persona persona = new Persona();
        persona.setNombre_persona(personaDTO.getNombre_persona());
        persona.setCorreo_persona(personaDTO.getCorreo_persona());
        persona.setTelefono_persona(personaDTO.getTelefono_persona());
        ValidatePersona.validatePersona(persona);
        return persona;
    }

    public static Persona editionPersonaDTOToPersona(PersonaDTO personaDTO) {
        Persona persona = new Persona();
        if (personaDTO.getNombre_persona() != null){
            persona.setNombre_persona(personaDTO.getNombre_persona());
        }
        if (personaDTO.getTelefono_persona() != null){
            persona.setTelefono_persona(personaDTO.getTelefono_persona());
        }
        ValidateEditionPersona.validateEditionPersona(persona);
        return persona;
    }
}
