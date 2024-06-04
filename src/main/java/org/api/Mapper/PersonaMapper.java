package org.api.Mapper;

import org.api.domain.Persona;
import org.api.domain.PersonaDTO;
import org.api.domain.PersonaEditDTO;
import org.api.validations.ValidatePersona;

public class PersonaMapper {

    public static Persona personaDTOToPersona(PersonaDTO personaDTO) {
        Persona persona = new Persona();
        persona.setNombre_persona(personaDTO.getNombre_persona());
        persona.setCorreo_persona(personaDTO.getCorreo_persona());
        persona.setTelefono_persona(personaDTO.getTelefono_persona());
        persona.setFecha_baja(personaDTO.getFecha_baja());
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
        if (personaDTO.getFecha_baja() != null){
            persona.setFecha_baja(personaDTO.getFecha_baja());
        }
        PersonaEditDTO.validateEditionPersona(persona);
        return persona;
    }
}
