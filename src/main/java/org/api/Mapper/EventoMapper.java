package org.api.Mapper;

import org.api.domain.*;
import org.api.validations.ValidateEvento;

public class EventoMapper {

    public static Evento eventoDTOToEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setNombre(eventoDTO.getNombre());
        evento.setHora_evento(eventoDTO.getHora_evento());
        evento.setFecha_evento(eventoDTO.getFecha_evento());
        evento.setEmpresa_evento(eventoDTO.getEmpresa_evento());
        ValidateEvento.validateEvento(evento);
        return evento;
    }

    public static Evento editionEventoDTOToEvento(EventoEditDTO eventoEditDTO) {
        Evento evento = new Evento();
        if(eventoEditDTO.getNombre() != null){
            evento.setNombre(eventoEditDTO.getNombre());
        }
        if (eventoEditDTO.getHora_evento() != null){
            evento.setHora_evento(eventoEditDTO.getHora_evento());
        }
        if(eventoEditDTO.getFecha_evento() != null){
            evento.setFecha_evento(eventoEditDTO.getFecha_evento());
        }
        EventoEditDTO.validateEditionEvento(evento);
        return evento;
    }
}
