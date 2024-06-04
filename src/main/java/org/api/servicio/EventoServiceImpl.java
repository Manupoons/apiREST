package org.api.servicio;

import org.api.domain.*;
import org.api.dao.IEventoDAO;
import org.api.dao.ICompraDAO;
import org.api.dao.IRelEventoPersonaDAO;
import org.api.validations.ValidateEvento;
import org.springframework.http.HttpStatus;
import org.api.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
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
    public List<Compra> listadoCompraPorEvento(IdValue idEvento){
        if (iCompraDAO.findByIdEvento(idEvento).isEmpty()){
            throw new InvalidURLException("Evento id not found");
        }
        return iCompraDAO.findByIdEvento(idEvento);
    }

//    @Override
//    @Transactional
//    public List<Evento> listadoEventosPorPersona(IdValue idPersona) {
//        if (iRelEventoPersonaDAO.findByPersonaIdPersona(idPersona).isEmpty()) {
//            throw new InvalidURLException("Persona id not found");
//        }
//        List<RelEventoPersona> relEventoPersonas = iRelEventoPersonaDAO.findByPersonaIdPersona(idPersona);
//        List<Evento> eventos = new ArrayList<>();
//        for (RelEventoPersona rel : relEventoPersonas) {
//            Long eventoId = rel.getEvento().getIdEvento();
//            iEventoDAO.findByIdEvento(eventoId).ifPresent(eventos::add);
//        }
//        return eventos;
//    }

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
        if(iEventoDAO.findByIdEvento(id)==null){
            throw new InvalidURLException("The evento with this id doesn't exist");
        }
        Evento eventoEditado = iEventoDAO.findByIdEvento(id);
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
        if (iEventoDAO.findByIdEvento(id) == null){
            throw new InvalidURLException("The evento with this id doesn't exist");
        }
        iEventoDAO.deleteByIdEvento(id);
    }
}
