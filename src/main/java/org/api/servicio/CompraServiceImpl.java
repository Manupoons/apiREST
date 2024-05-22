package org.api.servicio;

import java.util.List;
import org.api.domain.Compra;
import org.api.dao.ICompraDAO;
import org.api.validations.ValidateCompra;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.validations.ValidateEditionCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompraServiceImpl implements ICompraService{

    private final ICompraDAO iCompraDAO;

    @Autowired
    public CompraServiceImpl(ICompraDAO iCompraDao) {
        this.iCompraDAO = iCompraDao;
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
    public ResponseEntity<Compra> editarCompra(Long id, Compra compra) {
        ValidateEditionCompra.validateEditionCompra(compra);
        Compra compraEditada = iCompraDAO.findById(id).orElse(null);
        assert compraEditada != null;
        if (compra.getNumero_entradas() != null) {
            compraEditada.setNumero_entradas(compra.getNumero_entradas());
        }
        if (compra.getFecha_compra() != null) {
            compraEditada.setFecha_compra(compra.getFecha_compra());
        }
        if (compra.getId_evento() != null){
            compraEditada.setId_evento(compra.getId_evento());
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
