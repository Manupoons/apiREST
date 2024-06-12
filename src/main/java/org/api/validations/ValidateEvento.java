package org.api.validations;

import org.api.domain.Evento;
import java.util.regex.Pattern;
import org.api.exception.InvalidEventoException;

public class ValidateEvento {

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

        if (evento.getEmpresa_evento() == null) {
            throw new InvalidEventoException("The business name can't be empty");
        } else if (!evento.getEmpresa_evento().matches("^[a-zA-Z0-9\\s\\-,.&'\"]+$")) {
            throw new InvalidEventoException("The business name can only contain letters and spaces");
        }
    }

    private static boolean isValidTime(String time) {
        if (time == null) {
            return false;
        }
        return TIME_PATTERN.matcher(time).matches();
    }
}
