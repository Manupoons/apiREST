package org.api.web;

import java.util.List;
import org.api.domain.*;
import org.api.Mapper.EventoMapper;
import org.api.Mapper.PersonaMapper;
import org.api.Mapper.CompraMapper;
import org.api.servicio.ICompraService;
import org.api.servicio.IEventoService;
import org.api.servicio.IPersonaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    public List<Compra> inicio() {
        return iCompraService.listarCompras();
    }

    @GetMapping("/evento")
    public List<Evento> listar() {
        return iEventoService.listarEventos();
    }

    @GetMapping("/persona")
    public List<Persona> listarPersonas() {
        return iPersonaService.listarPersonas();
    }

    @GetMapping("/evento/compras/{idEvento}")
    public List<Compra> listarComprasPorEvento(@PathVariable Long idEvento) {
        return iEventoService.listadoCompraPorEvento(idEvento);
    }

    @GetMapping("/persona/{idEvento}")
    public List<Persona> listarPersonasPorEvento(@PathVariable Long idEvento) {
        return iPersonaService.listadoPersonasPorEvento(idEvento);
    }

    @GetMapping("/evento/{idPersona}")
    public List<Evento> listarEventosPorPersona(@PathVariable Long idPersona) {
        return iEventoService.listadoEventosPorPersona(idPersona);
    }

    @PostMapping("/compras/guardar")
    public Compra nuevaCompra(@RequestBody CompraDTO compraDTO) {
        return iCompraService.nuevaCompra(compraMapper.compraDTOToCompra(compraDTO)).getBody();
    }

    @PostMapping("/evento/guardar")
    public Evento nuevoEvento(@RequestBody EventoDTO eventoDTO) {
        return iEventoService.nuevoEvento(EventoMapper.eventoDTOToEvento(eventoDTO)).getBody();
    }

    @PostMapping("/persona/guardar")
    public Persona nuevoPersona(@RequestBody PersonaDTO personaDTO) {
        return iPersonaService.nuevaPersona(PersonaMapper.personaDTOToPersona(personaDTO)).getBody();
    }

    @PutMapping("/compras/{idCompra}")
    public Compra editarCompra(@PathVariable Long idCompra, @RequestBody CompraDTO compraDTO) {
        return iCompraService.editarCompra(idCompra, CompraMapper.editionCompraDTOToCompra(compraDTO)).getBody();
    }

    @PutMapping("/evento/{idEvento}")
    public Evento editarEvento(@PathVariable Long idEvento, @RequestBody EventoDTO eventoDTO) {
        return iEventoService.editarEvento(idEvento, EventoMapper.editionEventoDTOToEvento(eventoDTO)).getBody();
    }

    @PutMapping("/persona/{idPersona}")
    public Persona editarPersona(@PathVariable Long idPersona, @RequestBody PersonaDTO personaDTO) {
        return iPersonaService.editarPersona(idPersona, PersonaMapper.editionPersonaDTOToPersona(personaDTO)).getBody();
    }

    @DeleteMapping("/compras/{idCompra}")
    public void eliminarCompra(@PathVariable Long idCompra) {
        iCompraService.eliminarCompra(idCompra);
    }

    @DeleteMapping("/evento/{idEvento}")
    public void eliminarEvento(@PathVariable Long idEvento) {
        iEventoService.eliminarEvento(idEvento);
    }

    @DeleteMapping("/persona/{idPersona}")
    public void eliminarPersona(@PathVariable Long idPersona) {
        iPersonaService.eliminarPersona(idPersona);
    }
}
