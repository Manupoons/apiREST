package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedEventoException;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.regex.Pattern;

@Data
public class EventoEditDTO implements Serializable {

    private String nombre_evento;

    private String hora_evento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_evento;

    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    public static void validateEditionEvento(Evento evento) {
        if(evento.getNombre() != null){
            if (!evento.getNombre().matches("^[\\w\\s@#&!\"'()\\-.,:;]+$")) {
                throw new InvalidEditedEventoException("The event name can only contain letters, certain special characters and spaces");
            }
        }
        if(evento.getHora_evento() != null){
            if (!isValidTime(evento.getHora_evento())){
                throw new InvalidEditedEventoException("Invalid time format. Expected format is HH:mm");
            }
        }
    }

    private static boolean isValidTime(String time) {
        if (time == null) {
            return false;
        }
        return TIME_PATTERN.matcher(time).matches();
    }
}
