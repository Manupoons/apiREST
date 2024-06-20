package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedEventoException;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class EventoEditDTO implements Serializable {

    private String nombre;

    private String hora_evento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fecha_evento;


    public static void validateEditionEvento(Evento evento) {
        if(evento.getNombre() != null){
            if (!evento.getNombre().matches("^[\\w\\s@#&!\"'()\\-.,:;]+$")) {
                throw new InvalidEditedEventoException("The event name can only contain letters, certain special characters and spaces");
            }
        }
    }
}
