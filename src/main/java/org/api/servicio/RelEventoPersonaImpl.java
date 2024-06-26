package org.api.servicio;

import org.api.dao.*;
import org.api.domain.*;
import org.api.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelEventoPersonaImpl implements IRelEventoPersonaService{

    private final IEventoDAO iEventoDAO;
    private final IPersonaDAO iPersonaDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    @Autowired
    public RelEventoPersonaImpl(IEventoDAO iEventoDAO, IPersonaDAO iPersonaDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
        this.iEventoDAO = iEventoDAO;
        this.iPersonaDAO = iPersonaDAO;
        this.iRelEventoPersonaDAO = iRelEventoPersonaDAO;
    }

    @Override
    @Transactional
    public List<RelEventoPersona> listarRelEventoPersonas() {
        return (List<RelEventoPersona>) iRelEventoPersonaDAO.findAll();
    }
    
    @Override
    @Transactional
    public void createRelEventoPersona(Evento eventoNuevo, Persona personaNueva){
        Evento evento = iEventoDAO.findById(eventoNuevo.getIdEvento()).orElseThrow(() -> new InvalidRelEventoPersona("Evento id not found"));
        Persona persona = iPersonaDAO.findById(personaNueva.getIdPersona()).orElseThrow(() -> new InvalidRelEventoPersona("Persona id not found"));
        List<RelEventoPersona> existRelEventoPersona = iRelEventoPersonaDAO.findByEventoIdEventoAndPersonaIdPersona(evento.getIdEvento(), persona.getIdPersona());
        if (!existRelEventoPersona.isEmpty()){
            throw new InvalidRelEventoPersona("Relation evento persona already exits");
        }
        RelEventoPersona relEventoPersona = new RelEventoPersona();
        relEventoPersona.setEvento(evento);
        relEventoPersona.setPersona(persona);
        iRelEventoPersonaDAO.save(relEventoPersona);
    }

    @Override
    @Transactional
    public void eliminarRelEventoPersona(IdValue id){
        iRelEventoPersonaDAO.findById(id.getValue()).orElseThrow(() -> new InvalidRelEventoPersona("Persona id not found"));
        iRelEventoPersonaDAO.deleteByIdEventoPersona(id.getValue());
    }
}
