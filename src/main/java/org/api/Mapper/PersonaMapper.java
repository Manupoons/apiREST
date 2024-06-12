package org.api.Mapper;

import org.api.domain.Persona;
import org.api.domain.PersonaDTO;
import org.api.domain.PersonaEditDTO;
import org.api.validations.ValidatePersona;

import java.text.SimpleDateFormat;

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

    public static Persona editionPersonaDTOToPersona(PersonaEditDTO personaEditDTO) {
        Persona persona = new Persona();
        if (personaEditDTO.getNombre_persona() != null){
            persona.setNombre_persona(personaEditDTO.getNombre_persona());
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
