package org.api.servicio;

import org.api.domain.*;
import org.api.dao.*;
import org.springframework.http.HttpStatus;
import org.api.validations.ValidatePersona;
import org.api.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final IEventoDAO iEventoDAO;
    private final ICompraDAO iCompraDAO;
    private final IPersonaDAO iPersonaDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    @Autowired
    public PersonaServiceImpl(IEventoDAO iEventoDAO, ICompraDAO iCompraDAO, IPersonaDAO iPersonaDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
        this.iEventoDAO = iEventoDAO;
        this.iCompraDAO = iCompraDAO;
        this.iPersonaDAO = iPersonaDAO;
        this.iRelEventoPersonaDAO = iRelEventoPersonaDAO;
    }

    @Override
    @Transactional
    public List<Persona> listarPersonas() {
        return (List<Persona>) iPersonaDAO.findAll();
    }

    @Override
    @Transactional
    public List<Compra> listadoCompraPorPersona(IdValue idPersona){
        iCompraDAO.findById(idPersona.getValue()).orElseThrow(() -> new InvalidURLException("Persona id not found"));
        return iCompraDAO.findByIdPersona(idPersona.getValue());
    }

    @Override
    @Transactional
    public List<Persona> listadoPersonasPorEvento(IdValue idEvento) {
        iEventoDAO.findById(idEvento.getValue()).orElseThrow(() -> new InvalidURLException("Evento id not found"));
        List<RelEventoPersona> rels = iRelEventoPersonaDAO.findByEventoIdEvento(idEvento.getValue());
        List<Long> idsPersona = rels.stream()
                                .map(rel -> rel.getPersona().getIdPersona())
                                .collect(Collectors.toList());

        return iPersonaDAO.findAllById(idsPersona);
    }

    @Override
    @Transactional
    public ResponseEntity<Persona> nuevaPersona(Persona persona) {
        ValidatePersona.validatePersona(persona);
        return new ResponseEntity<>(iPersonaDAO.save(persona), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Persona> editarPersona(IdValue id, Persona persona) {
        PersonaEditDTO.validateEditionPersona(persona);
        Persona personaEditada;
        personaEditada = iPersonaDAO.findById(id.getValue()).orElseThrow(() -> new InvalidURLException("The persona with this id doesn't exist"));
        if (persona.getNombre_persona() != null){
            personaEditada.setNombre_persona(persona.getNombre_persona());
        }
        if (persona.getTelefono_persona() != null){
            personaEditada.setTelefono_persona(persona.getTelefono_persona());
        }
        if (persona.getFecha_baja() != null){
            personaEditada.setFecha_baja(persona.getFecha_baja());
        }
        if (persona.getFecha_baja() == null){
            personaEditada.setFecha_baja(null);
        }
        return new ResponseEntity<>(iPersonaDAO.save(personaEditada), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void eliminarPersona(IdValue id) {
        iPersonaDAO.findById(id.getValue()).orElseThrow(() -> new InvalidURLException("The persona with this id doesn't exist"));
        Persona personaEditada = iPersonaDAO.findByIdPersona(id.getValue());
        personaEditada.setFecha_baja(String.valueOf(LocalDate.now()));
    }
}
