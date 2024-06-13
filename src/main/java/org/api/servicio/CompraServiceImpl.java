package org.api.servicio;

import org.api.dao.*;
import org.api.domain.*;
import org.api.validations.*;
import org.springframework.http.HttpStatus;
import org.api.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.exception.InvalidCompraException;
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
    public Compra nuevaCompra(Compra compra) {
        ValidateCompra.validateCompra(compra);
        return new ResponseEntity<>(iCompraDAO.save(compra), HttpStatus.CREATED).getBody();
    }

    @Override
    @Transactional
    public Compra nuevaCompraConPersona(Compra compra, IdValue idPersona) {
         Persona persona = iPersonaDAO.findById(idPersona.getValue()).orElseThrow(() -> new InvalidCompraException("Couldn't find persona with this id"));
         if (persona.getFecha_baja() == null) {
             ValidateCompraConPersona.validateCompraConPersona(compra);
             return new ResponseEntity<>(iCompraDAO.save(compra), HttpStatus.CREATED).getBody();
         }else{
             throw new InvalidCompraException("This person can't sell tickets");
         }
    }

    @Override
    @Transactional
    public Compra editarCompra(IdValue id, int numero_entradas) {
        ValidateEditionCompra.validateEditionCompra(numero_entradas);
        iCompraDAO.findById(id.getValue()).orElseThrow(() -> new InvalidURLException("The compra with this id doesn't exist"));
        Compra compraEditada = iCompraDAO.findByIdCompra(id.getValue());
        compraEditada.setNumero_entradas(numero_entradas);
        return new ResponseEntity<>(iCompraDAO.save(compraEditada), HttpStatus.OK).getBody();
    }

    @Override
    @Transactional
    public void eliminarCompra(IdValue id) {
        iCompraDAO.findById(id.getValue()).orElseThrow(() -> new InvalidURLException("The compra with this id doesn't exist"));
        iCompraDAO.deleteByIdCompra(id.getValue());
    }
}
