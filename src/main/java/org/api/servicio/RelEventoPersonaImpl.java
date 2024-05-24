package org.api.servicio;

import org.api.dao.IEventoDAO;
import org.api.dao.IPersonaDAO;
import org.api.dao.IRelEventoPersonaDAO;
import org.api.domain.Evento;
import org.api.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelEventoPersonaImpl implements IRelEventoPersonaService{

    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;
    private final IEventoDAO iEventoDAO;
    private final IPersonaDAO iPersonaDAO;

    @Autowired
    public RelEventoPersonaImpl(IRelEventoPersonaDAO iRelEventoPersonaDAO, IEventoDAO iEventoDAO, IPersonaDAO iPersonaDAO) {
        this.iRelEventoPersonaDAO = iRelEventoPersonaDAO;
        this.iEventoDAO = iEventoDAO;
        this.iPersonaDAO = iPersonaDAO;
    }


    @Override
    @Transactional
    public List<Evento> listadoEventosPorPersona(Long idPersona) {
        return iEventoDAO.findByPersonaIdPersona(idPersona);
    }

    @Override
    @Transactional
    public List<Persona> listadoPersonaPorEvento(Long idEvento) {
        return iPersonaDAO.findByEventoIdEvento(idEvento);
    }
}
