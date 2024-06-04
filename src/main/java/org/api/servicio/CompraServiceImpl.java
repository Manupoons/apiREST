package org.api.servicio;

import org.api.domain.*;
import org.api.dao.ICompraDAO;
import org.api.dao.IPersonaDAO;
import org.api.validations.ValidateCompra;
import org.springframework.http.HttpStatus;
import org.api.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.exception.InvalidCompraException;
import org.api.validations.ValidateEditionCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CompraServiceImpl implements ICompraService{

    private final ICompraDAO iCompraDAO;
    private final IPersonaDAO iPersonaDAO;

    @Autowired
    public CompraServiceImpl(ICompraDAO iCompraDao, IPersonaDAO iPersonaDAO) {
        this.iCompraDAO = iCompraDao;
        this.iPersonaDAO = iPersonaDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listarCompras() {
        return (List<Compra>) iCompraDAO.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<Compra> nuevaCompra(Compra compra, IdValue idPersona) {
        Persona persona = iPersonaDAO.findByIdPersona(idPersona);
        if (persona.getFecha_baja() != null) {
            throw new InvalidCompraException("This person can't sell tickets");
        }
        else{
            ValidateCompra.validateCompra(compra);
        }
        return new ResponseEntity<>(iCompraDAO.save(compra), HttpStatus.CREATED);
    }

//    @Override
//    @Transactional
//    public void createRelEventoPersona(Long idEvento, Long idPersona){
//        Evento evento = iEventoDAO.findById(idEvento).orElseThrow(() -> new InvalidURLException("Evento id not found"));
//        Persona persona = iPersonaDAO.findById(idPersona).orElseThrow(() -> new InvalidURLException("Persona id not found"));
//        List<RelEventoPersona> existRelEventoPersona = iRelEventoPersonaDAO.findByEventoIdEventoAndPersonaIdPersona(idEvento, idPersona);
//
//        if (existRelEventoPersona.isEmpty()){
//            throw new InvalidURLException("Rel evento persona id not found");
//        }
//        RelEventoPersona relEventoPersona = new RelEventoPersona();
//        relEventoPersona.setEvento(evento);
//        relEventoPersona.setPersona(persona);
//        iRelEventoPersonaDAO.save(relEventoPersona);
//    }

    @Override
    @Transactional
    public Compra editarCompra(IdValue id, int numero_entradas) {
        ValidateEditionCompra.validateEditionCompra(numero_entradas);
        if (iCompraDAO.findByIdCompra(id) == null){
            throw new InvalidURLException("The compra with this id doesn't exist");
        }
        Compra compraEditada = iCompraDAO.findByIdCompra(id);
        compraEditada.setNumero_entradas(numero_entradas);
        return new ResponseEntity<>(iCompraDAO.save(compraEditada), HttpStatus.OK).getBody();
    }

    @Override
    @Transactional
    public void eliminarCompra(IdValue id) {
        if (iCompraDAO.findByIdCompra(id)== null){
            throw new InvalidURLException("The compra with this id doesn't exist");
        }
        iCompraDAO.deleteByIdCompra(id);
    }
}
