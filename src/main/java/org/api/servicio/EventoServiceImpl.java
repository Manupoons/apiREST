package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.Evento;
import org.api.dao.IEventoDAO;
import org.api.dao.ICompraDAO;
import org.api.domain.RelEventoPersona;
import org.api.dao.IRelEventoPersonaDAO;
import org.api.validations.ValidateEvento;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.validations.ValidateEditionEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EventoServiceImpl implements IEventoService{

    private final IEventoDAO iEventoDAO;
    private final ICompraDAO iCompraDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    @Autowired
    public EventoServiceImpl(IEventoDAO eventoDAO, ICompraDAO iCompraDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
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
    public List<Compra> listadoCompraPorEvento(Long idEvento){
        return iCompraDAO.findByIdEvento(idEvento);
    }

    @Override
    @Transactional
    public List<Evento> listadoEventosPorPersona(Long idPersona) {
        List<RelEventoPersona> rels = iRelEventoPersonaDAO.findByPersonaIdPersona(idPersona);
        List<Evento> eventos = new ArrayList<>();
        for (RelEventoPersona rel : rels) {
            Long eventoId = rel.getEvento().getIdEvento();
            iEventoDAO.findById(eventoId).ifPresent(eventos::add);
        }
        return eventos;
    }

    @Override
    @Transactional
    public ResponseEntity<Evento> nuevoEvento(Evento evento) {
        ValidateEvento.validateEvento(evento);
        return new ResponseEntity<>(iEventoDAO.save(evento), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Evento> editarEvento(Long id, Evento evento) {
        ValidateEditionEvento.validateEditionEvento(evento);
        Evento eventoEditado = iEventoDAO.findById(id).orElse(null);
        assert eventoEditado != null;
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
    public void eliminarEvento(Long id) {
        try {
            iEventoDAO.deleteById(id);
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
