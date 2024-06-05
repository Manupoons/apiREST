package org.api.servicio;

import org.api.dao.*;
import org.api.domain.*;
import org.api.validations.ValidateEvento;
import org.springframework.http.HttpStatus;
import org.api.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements IEventoService{

    private final IPersonaDAO iPersonaDAO;
    private final IEventoDAO iEventoDAO;
    private final ICompraDAO iCompraDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    @Autowired
    public EventoServiceImpl(IPersonaDAO personaDAO, IEventoDAO eventoDAO, ICompraDAO iCompraDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
        this.iPersonaDAO = personaDAO;
        this.iEventoDAO = eventoDAO;
        this.iCompraDAO = iCompraDAO;
        this.iRelEventoPersonaDAO = iRelEventoPersonaDAO;
    }

    @Override
    @Transactional
    public List<Evento> listarEventos() {
        return (List<Evento>) iEventoDAO.findAll();
    }

    @Override
    @Transactional
    public List<Compra> listadoCompraPorEvento(IdValue idEvento){
        iCompraDAO.findById(idEvento.getValue()).orElseThrow(() -> new InvalidURLException("Evento id not found"));
        return iCompraDAO.findByIdEvento(idEvento.getValue());
    }

    @Override
    @Transactional
    public List<Evento> listadoEventosPorPersona(IdValue idPersona) {
        iPersonaDAO.findById(idPersona.getValue()).orElseThrow(() -> new InvalidURLException("Persona id not found"));
        List<RelEventoPersona> rels = iRelEventoPersonaDAO.findByPersonaIdPersona(idPersona.getValue());
        List<Long> idsEventos = rels.stream()
                                .map(rel -> rel.getEvento().getIdEvento())
                                .collect(Collectors.toList());
        return iEventoDAO.findAllById(idsEventos);
    }

    @Override
    @Transactional
    public ResponseEntity<Evento> nuevoEvento(Evento evento) {
        ValidateEvento.validateEvento(evento);
        return new ResponseEntity<>(iEventoDAO.save(evento), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Evento> editarEvento(IdValue id, Evento evento) {
        EventoEditDTO.validateEditionEvento(evento);
        iEventoDAO.findById(id.getValue()).orElseThrow(() -> new InvalidURLException("The evento with this id doesn't exist"));
        Evento eventoEditado = iEventoDAO.findByIdEvento(id.getValue());
        if (evento.getNombre_evento() != null){
            eventoEditado.setNombre_evento(evento.getNombre_evento());
        }
        if (evento.getHora_evento() != null) {
            eventoEditado.setHora_evento(evento.getHora_evento());
        }
        if (evento.getFecha_evento() != null) {
            eventoEditado.setFecha_evento(evento.getFecha_evento());
        }
        return new ResponseEntity<>(iEventoDAO.save(eventoEditado), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void eliminarEvento(IdValue id) {
        iEventoDAO.findById(id.getValue()).orElseThrow(() -> new InvalidURLException("The evento with this id doesn't exist"));
        iEventoDAO.deleteByIdEvento(id.getValue());
    }
}
