package org.api.Mapper;

import org.api.domain.Evento;
import org.api.domain.EventoDTO;
import org.api.validations.ValidateEditionEvento;
import org.api.validations.ValidateEvento;

public class EventoMapper {

    public static Evento eventoDTOToEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setNombre_evento(eventoDTO.getNombre_evento());
        evento.setHora_evento(eventoDTO.getHora_evento());
        evento.setFecha_evento(eventoDTO.getFecha_evento());
        evento.setEmpresa_evento(eventoDTO.getEmpresa_evento());
        ValidateEvento.validateEvento(evento);
        return evento;
    }

    public static Evento editionEventoDTOToEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        if (eventoDTO.getHora_evento() != null){
            evento.setHora_evento(eventoDTO.getHora_evento());
        }
        if(eventoDTO.getFecha_evento() != null){
            evento.setFecha_evento(eventoDTO.getFecha_evento());
        }
        if(eventoDTO.getEmpresa_evento() != null){
            evento.setEmpresa_evento(eventoDTO.getEmpresa_evento());
        }
        ValidateEditionEvento.validateEditionEvento(evento);
        return evento;
    }
}
