package org.api.servicio;

import org.api.domain.*;
import org.api.dao.IEventoDAO;
import org.api.dao.ICompraDAO;
import org.api.dao.IPersonaDAO;
import org.api.dao.IRelEventoPersonaDAO;
import org.api.validations.ValidateCompra;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.validations.ValidateEditionCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CompraServiceImpl implements ICompraService{

    private final ICompraDAO iCompraDAO;
    private final IEventoDAO iEventoDAO;
    private final IPersonaDAO iPersonaDAO;
    private final IRelEventoPersonaDAO iRelEventoPersonaDAO;

    @Autowired
    public CompraServiceImpl(ICompraDAO iCompraDao, IEventoDAO iEventoDAO, IPersonaDAO iPersonaDAO, IRelEventoPersonaDAO iRelEventoPersonaDAO) {
        this.iCompraDAO = iCompraDao;
        this.iEventoDAO = iEventoDAO;
        this.iPersonaDAO = iPersonaDAO;
        this.iRelEventoPersonaDAO = iRelEventoPersonaDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listarCompras() {
        return (List<Compra>) iCompraDAO.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<Compra> nuevaCompra(Compra compra) {
        ValidateCompra.validateCompra(compra);
        return new ResponseEntity<>(iCompraDAO.save(compra), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public void createRelEventoPersona(Long idEvento, Long idPersona){
        Evento evento = iEventoDAO.findById(idEvento).orElseThrow(() -> new IllegalArgumentException("Invalid evento ID"));
        Persona persona = iPersonaDAO.findById(idPersona).orElseThrow(() -> new IllegalArgumentException("Invalid persona ID"));

        List<RelEventoPersona> existRelEventoPersona = iRelEventoPersonaDAO.findByEventoIdEventoAndPersonaIdPersona(idEvento, idPersona);
        if (!existRelEventoPersona.isEmpty()){
            existRelEventoPersona.getFirst();
            return;
        }
        RelEventoPersona relEventoPersona = new RelEventoPersona();
        relEventoPersona.setEvento(evento);
        relEventoPersona.setPersona(persona);
        iRelEventoPersonaDAO.save(relEventoPersona);
    }

    @Override
    @Transactional
    public ResponseEntity<Compra> editarCompra(Long id, Compra compra) {
        ValidateEditionCompra.validateEditionCompra(compra);
        Compra compraEditada = iCompraDAO.findById(id).orElse(null);
        assert compraEditada != null;
        if (compra.getNumero_entradas() != null) {
            compraEditada.setNumero_entradas(compra.getNumero_entradas());
        }
        return new ResponseEntity<>(iCompraDAO.save(compraEditada), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void eliminarCompra(Long id) {
        try {
            iCompraDAO.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
