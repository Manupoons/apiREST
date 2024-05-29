package org.api.servicio;

import org.api.dao.IPersonaDAO;
import org.api.dao.IRelEventoPersonaDAO;
import org.api.domain.Persona;
import org.api.domain.RelEventoPersona;
import org.api.validations.ValidateEditionPersona;
import org.api.validations.ValidatePersona;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final IPersonaDAO iPersonaDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    public PersonaServiceImpl(IPersonaDAO iPersonaDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
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
    public List<Persona> listadoPersonasPorEvento(Long idEvento) {
        List<RelEventoPersona> rels = iRelEventoPersonaDAO.findByEventoIdEvento(idEvento);
        List<Persona> personas = new ArrayList<>();
        for (RelEventoPersona rel : rels) {
            Long personaID = rel.getPersona().getIdPersona();
            iPersonaDAO.findById(personaID).ifPresent(personas::add);
        }
        return personas;
    }

    @Override
    @Transactional
    public ResponseEntity<Persona> nuevaPersona(Persona persona) {
        ValidatePersona.validatePersona(persona);
        return new ResponseEntity<>(iPersonaDAO.save(persona), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Persona> editarPersona(Long id, Persona persona) {
        ValidateEditionPersona.validateEditionPersona(persona);
        Persona personaEditada = iPersonaDAO.findById(id).orElse(null);
        assert personaEditada != null;
        if (persona.getNombre_persona() != null){
            personaEditada.setNombre_persona(persona.getNombre_persona());
        }
        if (persona.getCorreo_persona() != null){
            personaEditada.setCorreo_persona(persona.getCorreo_persona());
        }
        if (persona.getTelefono_persona() != null){
            personaEditada.setTelefono_persona(persona.getTelefono_persona());
        }
        return new ResponseEntity<>(iPersonaDAO.save(personaEditada), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void eliminarPersona(Long id) {
        try {
            iPersonaDAO.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
