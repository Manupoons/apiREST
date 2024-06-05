package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedPersonaException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
public class PersonaEditDTO implements Serializable {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String nombre_persona;

    private String telefono_persona;

    private String fecha_baja;

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

        if(persona.getFecha_baja() != null){
            if (!isValidDate(persona.getFecha_baja())){
                throw new InvalidEditedPersonaException("The date format is not right");
            }
        }
    }

    private static boolean isValidDate(String date){
        try{
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        }catch (DateTimeParseException e){
            return false;
        }
    }
}

