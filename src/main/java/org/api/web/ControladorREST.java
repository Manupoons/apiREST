package org.api.web;

import java.util.List;

import org.api.Mapper.EventoMapper;
import org.api.domain.Compra;
import org.api.domain.CompraDTO;
import org.api.Mapper.CompraMapper;
import org.api.domain.Evento;
import org.api.domain.EventoDTO;
import org.api.servicio.ICompraService;
import org.api.servicio.IEventoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class ControladorREST {

    private final ICompraService iCompraService;
    private final IEventoService iEventoService;

    @Autowired
    public ControladorREST(ICompraService iCompraService, IEventoService iEventoService) {
        this.iCompraService = iCompraService;
        this.iEventoService = iEventoService;
    }

    @GetMapping("/compras")
    public List<Compra> inicio() {
        return iCompraService.listarCompras();
    }

    @GetMapping("/evento")
    public List<Evento> listar() {
        return iEventoService.listarEventos();
    }

    @GetMapping("/evento/compras/{id_evento}")
    public List<Compra> listarEvento(@PathVariable Long id_evento) {
        return iEventoService.listadoCompraPorEvento(id_evento);
    }

    @PostMapping("/compras/guardar")
    public Compra nuevaCompra(@RequestBody CompraDTO compraDTO) {
        return iCompraService.nuevaCompra(CompraMapper.compraDTOToCompra(compraDTO)).getBody();
    }

    @PostMapping("/evento/guardar")
    public Evento nuevoEvento(@RequestBody EventoDTO eventoDTO) {
        return iEventoService.nuevoEvento(EventoMapper.eventoDTOToEvento(eventoDTO)).getBody();
    }

    @PutMapping("/compras/{id_compra}")
    public Compra editarCompra(@PathVariable Long id_compra, @RequestBody CompraDTO compraDTO) {
        return iCompraService.editarCompra(id_compra, CompraMapper.editionCompraDTOToCompra(compraDTO)).getBody();
    }

    @PutMapping("/evento/{id_evento}")
    public Evento editarEvento(@PathVariable Long id_evento, @RequestBody EventoDTO eventoDTO) {
        return iEventoService.editarEvento(id_evento, EventoMapper.editionEventoDTOToEvento(eventoDTO)).getBody();
    }

    @DeleteMapping("/compras/{id_compra}")
    public void eliminarCompra(@PathVariable Long id_compra) {
        iCompraService.eliminarCompra(id_compra);
    }

    @DeleteMapping("/evento/{id_evento}")
    public void eliminarEvento(@PathVariable Long id_evento) {
        iEventoService.eliminarEvento(id_evento);
    }
}
