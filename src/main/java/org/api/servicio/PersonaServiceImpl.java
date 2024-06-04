package org.api.servicio;

import org.api.domain.*;
import org.api.dao.ICompraDAO;
import org.api.dao.IPersonaDAO;
import org.api.dao.IRelEventoPersonaDAO;
import org.api.validations.ValidatePersona;
import org.springframework.http.HttpStatus;
import org.api.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final ICompraDAO iCompraDAO;
    private final IPersonaDAO iPersonaDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    @Autowired
    public PersonaServiceImpl(ICompraDAO iCompraDAO, IPersonaDAO iPersonaDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
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
        if (iCompraDAO.findByIdPersona(idPersona).isEmpty()) {
            throw new InvalidURLException("Persona id not found");
        }
        return iCompraDAO.findByIdPersona(idPersona);
    }

//    @Override
//    @Transactional
//    public List<Persona> listadoPersonasPorEvento(IdValue idEvento) {
//        if (iRelEventoPersonaDAO.findByIdEventoPersona(idEvento)==null) {
//            throw new InvalidURLException("Evento id not found");
//        }
//        List<RelEventoPersona> rels = iRelEventoPersonaDAO.findByEventoIdEvento(idEvento);
//        List<Persona> personas = new ArrayList<>();
//        for (RelEventoPersona rel : rels) {
//            IdValue personaID = rel.getPersona().getIdPersona();
//            iPersonaDAO.findByIdPersona(personaID).ifPresent(personas::add);
//        }
//        return personas;
//    }

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
        Persona personaEditada = iPersonaDAO.findByIdPersona(id);
        if (persona.getNombre_persona() != null){
            personaEditada.setNombre_persona(persona.getNombre_persona());
        }
        if (persona.getTelefono_persona() != null){
            personaEditada.setTelefono_persona(persona.getTelefono_persona());
        }
        if (persona.getFecha_baja() != null){
            personaEditada.setFecha_baja(persona.getFecha_baja());
        }
        return new ResponseEntity<>(iPersonaDAO.save(personaEditada), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void eliminarPersona(IdValue id) {
        if (iPersonaDAO.findByIdPersona(id)==null) {
            throw new InvalidURLException("The persona with this id doesn't exist");
        }
        Persona personaEditada = iPersonaDAO.findByIdPersona(id);
        personaEditada.setFecha_baja(String.valueOf(LocalDate.now()));
    }
}
