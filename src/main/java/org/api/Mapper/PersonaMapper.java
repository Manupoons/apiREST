package org.api.Mapper;

import org.api.domain.*;
import org.api.validations.ValidatePersona;

public class PersonaMapper {

    public static Persona personaDTOToPersona(PersonaDTO personaDTO) {
        Persona persona = new Persona();
        persona.setNombre(personaDTO.getNombre());
        persona.setCorreo(personaDTO.getCorreo());
        persona.setTelefono_persona(personaDTO.getTelefono_persona());
        persona.setFecha_baja(personaDTO.getFecha_baja());
        ValidatePersona.validatePersona(persona);
        return persona;
    }

    public static Persona editionPersonaDTOToPersona(PersonaEditDTO personaEditDTO) {
        Persona persona = new Persona();
        if (personaEditDTO.getNombre() != null){
            persona.setNombre(personaEditDTO.getNombre());
        }
        if (personaEditDTO.getTelefono_persona() != null){
            persona.setTelefono_persona(personaEditDTO.getTelefono_persona());
        }
        if (personaEditDTO.getFecha_baja() != null){
            persona.setFecha_baja(personaEditDTO.getFecha_baja());
        }
        if (personaEditDTO.getFecha_baja() == null){
            persona.setFecha_baja(null);
        }
        PersonaEditDTO.validateEditionPersona(persona);
        return persona;
    }
}
