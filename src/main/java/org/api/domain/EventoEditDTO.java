package org.api.domain;

import lombok.Data;
import org.api.exception.InvalidEditedEventoException;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
public class EventoEditDTO implements Serializable {

    private String nombre_evento;

    private String hora_evento;

    private String fecha_evento;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    public static void validateEditionEvento(Evento evento) {
        if(evento.getNombre_evento() != null){
            if (!evento.getNombre_evento().matches("^[\\w\\s@#&!\"'()\\-.,:;]+$")) {
                throw new InvalidEditedEventoException("The event name can only contain letters, certain special characters and spaces");
            }
        }
        if(evento.getHora_evento() != null){
            if (!isValidTime(evento.getHora_evento())){
                throw new InvalidEditedEventoException("Invalid time format. Expected format is HH:mm");
            }
        }
        if(evento.getFecha_evento() != null){
            if (!isValidDate(evento.getFecha_evento())){
                throw new InvalidEditedEventoException("Invalid date format. Expected format is yyyy-MM-dd");
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

    private static boolean isValidTime(String time) {
        if (time == null) {
            return false;
        }
        return TIME_PATTERN.matcher(time).matches();
    }
}
