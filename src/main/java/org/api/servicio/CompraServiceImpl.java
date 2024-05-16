package org.api.servicio;

import java.util.List;
import java.util.Objects;
import org.api.domain.Compra;
import org.api.dao.ICompraDAO;
import org.api.validations.ValidateCompra;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.api.exception.InvalidCompraException;
import org.api.validations.ValidateEditionCompra;
import org.api.exception.InvalidEditedCompraException;
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
        try {
            ValidateCompra.validateCompra(compra);
            return new ResponseEntity<>(iCompraDAO.save(compra), HttpStatus.CREATED);
        } catch (InvalidCompraException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Compra> editarCompra(Long id, Compra compra) {
        Compra compraEditada = iCompraDAO.findById(id).orElse(null);
        try {
            if (compraEditada != null) {
                ValidateEditionCompra.validateEditionCompra(compra);
                compraEditada.setNombre_cliente(compra.getNombre_cliente());
                if (compra.getNumero_entradas() != null) {
                    compraEditada.setNumero_entradas(compra.getNumero_entradas());
                }
                if (compra.getFecha_compra() != null) {
                    compraEditada.setFecha_compra(compra.getFecha_compra());
                }
                return new ResponseEntity<>(iCompraDAO.save(compraEditada), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (InvalidEditedCompraException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void eliminarCompra(Long id) {
        try {
            iCompraDAO.delete(Objects.requireNonNull(iCompraDAO.findById(id).orElse(null)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
