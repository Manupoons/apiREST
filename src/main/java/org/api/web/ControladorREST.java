package org.api.web;

import java.util.List;
import org.api.domain.Compra;
import org.api.domain.CompraDTO;
import org.api.domain.CompraMapper;
import org.api.servicio.ICompraService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/compras")
public class ControladorREST {

    private final ICompraService iCompraService;

    @Autowired
    public ControladorREST(ICompraService iCompraService) {
        this.iCompraService = iCompraService;
    }

    @GetMapping
    public List<Compra> inicio() {
        return iCompraService.listarCompras();
    }

    @PostMapping("/guardar")
    public Compra nuevaCompra(@RequestBody CompraDTO compraDTO) {
        return iCompraService.nuevaCompra(CompraMapper.compraDTOToCompra(compraDTO)).getBody();
    }

    @PutMapping("/{idCompra}")
    public Compra editarCompra(@PathVariable Long idCompra, @RequestBody CompraDTO compraDTO) {
        return iCompraService.editarCompra(idCompra, CompraMapper.compraDTOToCompra(compraDTO)).getBody();
    }

    @DeleteMapping("/{idCompra}")
    public void eliminarCompra(@PathVariable Long idCompra) {
        iCompraService.eliminarCompra(idCompra);
    }
}
