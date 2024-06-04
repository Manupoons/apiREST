package org.api.validations;

import java.time.format.*;
import java.time.LocalDate;
import org.api.domain.Evento;
import java.util.regex.Pattern;
import org.api.exception.InvalidEventoException;

public class ValidateEvento {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    public static void validateEvento(Evento evento) {

        if (evento.getNombre_evento() == null) {
            throw new InvalidEventoException("The event name can't be empty");
        } else if (!evento.getNombre_evento().matches("^[\\w\\s@#&!\"'()\\-.,:;]+$")) {
            throw new InvalidEventoException("The event name can only contain letters, certain special characters and spaces");
        }

        if(evento.getHora_evento() != null){
            if (!isValidTime(evento.getHora_evento())){
                throw new InvalidEventoException("Invalid time format. Expected format is HH:mm");
            }
        }

        if (evento.getFecha_evento() != null){
            if (!isValidDate(evento.getFecha_evento())) {
                throw new InvalidEventoException("Invalid date format. Expected format is yyyy-MM-dd");
            }
        }

        if (evento.getEmpresa_evento() == null) {
            throw new InvalidEventoException("The business name can't be empty");
        } else if (!evento.getEmpresa_evento().matches("^[a-zA-Z0-9\\s\\-,.&'\"]+$")) {
            throw new InvalidEventoException("The business name can only contain letters and spaces");
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
