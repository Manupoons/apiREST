package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.Evento;
import org.api.dao.IEventoDAO;
import org.api.dao.ICompraDAO;
import org.api.validations.ValidateEvento;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.validations.ValidateEditionEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoServiceImpl implements IEventoService{

    private final IEventoDAO iEventoDAO;
    private final ICompraDAO iCompraDAO;

    @Autowired
    public EventoServiceImpl(IEventoDAO eventoDAO, ICompraDAO iCompraDAO) {
        this.iEventoDAO = eventoDAO;
        this.iCompraDAO = iCompraDAO;
    }

    @Override
    @Transactional
    public List<Evento> listarEventos() {
        return (List<Evento>) iEventoDAO.findAll();
    }

    @Override
    @Transactional
    public List<Compra> listadoCompraPorEvento(Long id_evento){
        return iCompraDAO.findById_evento(id_evento);
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
        if (evento.getHora_evento() != null) {
            eventoEditado.setHora_evento(evento.getHora_evento());
        }
        if (evento.getFecha_evento() != null) {
            eventoEditado.setFecha_evento(evento.getFecha_evento());
        }
        if (evento.getEmpresa_evento() != null) {
            eventoEditado.setEmpresa_evento(evento.getEmpresa_evento());
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
