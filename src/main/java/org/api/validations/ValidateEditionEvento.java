package org.api.validations;

import org.api.domain.Evento;
import org.api.exception.InvalidEditedEventoException;

import java.time.LocalDate;
import java.time.format.*;
import java.util.regex.Pattern;

public class ValidateEditionEvento {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    public static void validateEditionEvento(Evento evento) {
        if(evento.getNombre_evento() != null){
            if (!evento.getNombre_evento().matches("^[a-zA-Z\\s]+$")) {
                throw new InvalidEditedEventoException("The event name can only contain letters and spaces");
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
            if (LocalDate.parse(evento.getFecha_evento(), DATE_FORMATTER).isAfter(LocalDate.now())) {
                throw new InvalidEditedEventoException("The purchase date can't be in the future");
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
