package org.api.web;

import org.api.Mapper.*;
import org.api.domain.*;
import org.api.servicio.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ControladorREST {

    private final ICompraService iCompraService;
    private final IEventoService iEventoService;
    private final IPersonaService iPersonaService;
    private final CompraMapper compraMapper;

    @Autowired
    public ControladorREST(ICompraService iCompraService, IEventoService iEventoService, IPersonaService iPersonaService, CompraMapper compraMapper) {
        this.iCompraService = iCompraService;
        this.iEventoService = iEventoService;
        this.iPersonaService = iPersonaService;
        this.compraMapper = compraMapper;
    }

    @GetMapping("/compras")
    public List<Compra> listarCompras() {
        return iCompraService.listarCompras();
    }

    @GetMapping("/compras/evento/{idEvento}")
    public List<Compra> listarComprasPorEvento(@PathVariable IdValue idEvento) {
        return iEventoService.listadoCompraPorEvento(idEvento);
    }

    @GetMapping("/compras/persona/{idPersona}")
    public List<Compra> listarComprasPorPersona(@PathVariable IdValue idPersona) {
        return iPersonaService.listadoCompraPorPersona(idPersona);
    }

    @GetMapping("/eventos")
    public List<Evento> listarEventos() {
        return iEventoService.listarEventos();
    }

//    @GetMapping("/personas/evento/{idEvento}")
//    public List<Persona> listarPersonasPorEvento(@PathVariable IdValue idEvento) {
//        return iPersonaService.listadoPersonasPorEvento(idEvento);
//    }
//
//    @GetMapping("/eventos/persona/{idPersona}")
//    public List<Evento> listarEventosPorPersona(@PathVariable IdValue idPersona) {
//        return iEventoService.listadoEventosPorPersona(idPersona);
//    }

    @GetMapping("/personas")
    public List<Persona> listarPersonas() {
        return iPersonaService.listarPersonas();
    }

    @PostMapping("/compra/guardar/{idEvento}/{idPersona}")
    public ResponseEntity<Compra> nuevaCompra(@RequestBody CompraDTO compraDTO, @PathVariable IdValue idEvento, @PathVariable IdValue idPersona) {
        //iCompraService.createRelEventoPersona(idEvento, idPersona);
        return iCompraService.nuevaCompra(compraMapper.compraDTOToCompra(compraDTO, idEvento, idPersona), idPersona);
    }

    @PostMapping("/evento/guardar")
    public Evento nuevoEvento(@RequestBody EventoDTO eventoDTO) {
        return iEventoService.nuevoEvento(EventoMapper.eventoDTOToEvento(eventoDTO)).getBody();
    }

    @PostMapping("/persona/guardar")
    public Persona nuevaPersona(@RequestBody PersonaDTO personaDTO) {
        return iPersonaService.nuevaPersona(PersonaMapper.personaDTOToPersona(personaDTO)).getBody();
    }

    @PutMapping("/compra/{idCompra}/num_entradas/{numero_entradas}")
    public Compra editarCompra(@PathVariable IdValue idCompra, @PathVariable int numero_entradas) {
        return iCompraService.editarCompra(idCompra, numero_entradas);
    }

    @PutMapping("/evento/{idEvento}")
    public Evento editarEvento(@PathVariable IdValue idEvento, @RequestBody EventoEditDTO eventoEditDTO) {
        return iEventoService.editarEvento(idEvento, EventoMapper.editionEventoDTOToEvento(eventoEditDTO)).getBody();
    }

    @PutMapping("/persona/{idPersona}")
    public Persona editarPersona(@PathVariable IdValue idPersona, @RequestBody PersonaDTO personaDTO) {
        return iPersonaService.editarPersona(idPersona, PersonaMapper.editionPersonaDTOToPersona(personaDTO)).getBody();
    }

    @DeleteMapping("/compra/{idCompra}")
    public void eliminarCompra(@PathVariable IdValue idCompra) {
        iCompraService.eliminarCompra(idCompra);
    }

    @DeleteMapping("/evento/{idEvento}")
    public void eliminarEvento(@PathVariable IdValue idEvento) {
        iEventoService.eliminarEvento(idEvento);
    }

    @DeleteMapping("/persona/{idPersona}")
    public void eliminarPersona(@PathVariable IdValue idPersona) {
        iPersonaService.eliminarPersona(idPersona);
    }
}
