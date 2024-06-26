package org.api.web;

import org.api.dao.*;
import org.api.domain.*;
import org.api.Mapper.*;
import org.api.servicio.*;
import org.api.exception.InvalidURLException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@org.springframework.stereotype.Controller
@RequestMapping("/api")
public class Controller {

    private final CompraMapper compraMapper;
    private final IEventoDAO iEventoDAO;
    private final IPersonaDAO iPersonaDAO;
    private final IEventoService iEventoService;
    private final ICompraService iCompraService;
    private final IPersonaService iPersonaService;
    private final IRelEventoPersonaService iRelEventoPersonaService;

    @Autowired
    public Controller(ICompraService iCompraService, IEventoService iEventoService, IEventoDAO iEventoDAO, IPersonaService iPersonaService, CompraMapper compraMapper, IPersonaDAO iPersonaDAO, IRelEventoPersonaService iRelEventoPersonaService) {
        this.iEventoDAO = iEventoDAO;
        this.iPersonaDAO = iPersonaDAO;
        this.compraMapper = compraMapper;
        this.iCompraService = iCompraService;
        this.iEventoService = iEventoService;
        this.iPersonaService = iPersonaService;
        this.iRelEventoPersonaService = iRelEventoPersonaService;
    }

    @GetMapping()
    public ModelAndView index() {
        return new ModelAndView("Index/index");
    }

    //------------------------------------------------------------------------------------------------------------------

//    @GetMapping("/compras")
//    public ModelAndView listarCompras() {
//        List<Compra> compras = iCompraService.listarCompras();
//        return new ModelAndView("Compras/Compras").addObject("compras", compras);
//    }
//
//    @GetMapping("/compras/evento/{idEvento}")
//    public List<Compra> listarComprasPorEvento(@PathVariable IdValue idEvento) {
//        return iEventoService.listadoCompraPorEvento(idEvento);
//    }
//
//    @GetMapping("/compras/persona/{idPersona}")
//    public List<Compra> listarComprasPorPersona(@PathVariable IdValue idPersona) {
//        return iPersonaService.listadoCompraPorPersona(idPersona);
//    }
//
//    @GetMapping("/compra/nueva")
//    public ModelAndView nuevaCompra() {
//        return new ModelAndView("Compras/Compras").addObject("compra", new Compra());
//    }
//
//    @PostMapping("/compra/guardar/{idEvento}")
//    public Compra nuevaCompra(@RequestBody CompraDTO compraDTO, @PathVariable IdValue idEvento) {
//        return iCompraService.nuevaCompra(compraMapper.compraDTOToCompra(compraDTO, idEvento));
//    }
//
//    @PostMapping("/compra/guardar/{idEvento}/{idPersona}")
//    public Compra nuevaCompraConPersona(@RequestBody CompraDTO compraDTO, @PathVariable IdValue idEvento, @PathVariable IdValue idPersona) {
//        return iCompraService.nuevaCompraConPersona(compraMapper.compraDTOToCompraConPersona(compraDTO, idEvento, idPersona), idPersona);
//    }
//
//    @PutMapping("/compra/{idCompra}/num_entradas/{numero_entradas}")
//    public Compra editarCompra(@PathVariable IdValue idCompra, @PathVariable int numero_entradas) {
//        return iCompraService.editarCompra(idCompra, numero_entradas);
//    }
//
//    @DeleteMapping("/compra/eliminar/{idCompra}")
//    public void eliminarCompra(@PathVariable IdValue idCompra) {
//        iCompraService.eliminarCompra(idCompra);
//    }

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/eventos")
    public ModelAndView listarEventos() {
        List<Evento> eventos = iEventoService.listarEventos();
        return new ModelAndView("Eventos/Eventos").addObject("eventos", eventos);
    }

//    @GetMapping("/eventos/persona/{idPersona}")
//    public List<Evento> listarEventosPorPersona(@PathVariable IdValue idPersona) {
//        return iEventoService.listadoEventosPorPersona(idPersona);
//    }

    @GetMapping("/evento/nuevo")
    public ModelAndView nuevoEvento() {
        return new ModelAndView("Eventos/NuevoEvento").addObject("evento", new Evento());
    }

    @PostMapping("/evento/guardar")
    public String nuevoEvento(EventoDTO eventoDTO) {
        iEventoService.nuevoEvento(EventoMapper.eventoDTOToEvento(eventoDTO)).getBody();
        return "redirect:/api/eventos";
    }

    @GetMapping("/evento/editar/{idEvento}")
    public ModelAndView editarEvento(@PathVariable IdValue idEvento){
        Evento eventoEditar = iEventoDAO.findById(idEvento.getValue()).orElseThrow(() -> new InvalidURLException("The evento with this id doesn't exist"));
        return new ModelAndView("Eventos/EditarEvento").addObject("evento", eventoEditar);
    }

    @PostMapping("/evento/editar/{idEvento}")
    public String editarEvento(@PathVariable IdValue idEvento, EventoEditDTO eventoEditDTO) {
        iEventoService.editarEvento(idEvento, EventoMapper.editionEventoDTOToEvento(eventoEditDTO)).getBody();
        return "redirect:/api/eventos";
    }

    @GetMapping("/evento/eliminar/{idEvento}")
    public String eliminarEvento(@PathVariable IdValue idEvento) {
        iEventoService.eliminarEvento(idEvento);
        return "redirect:/api/eventos";
    }

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/personas")
    public ModelAndView listarPersonas() {
        List<Persona> personas = iPersonaService.listarPersonas();
        return new ModelAndView("Personas/Personas").addObject("personas", personas);
    }

    @GetMapping("/personas/evento/{idEvento}")
    public List<Persona> listarPersonasPorEvento(@PathVariable IdValue idEvento) {
        return iPersonaService.listadoPersonasPorEvento(idEvento);
    }

    @GetMapping("/persona/nueva")
    public ModelAndView nuevaPersona() {
        return new ModelAndView("Personas/NuevaPersona").addObject("persona", new Persona());
    }

    @PostMapping("/persona/guardar")
    public String nuevaPersona(PersonaDTO personaDTO) {
        iPersonaService.nuevaPersona(PersonaMapper.personaDTOToPersona(personaDTO)).getBody();
        return "redirect:/api/personas";
    }

    @GetMapping("/persona/editar/{idPersona}")
    public ModelAndView editarPersona(@PathVariable IdValue idPersona) {
        Persona persona = iPersonaDAO.findById(idPersona.getValue()).orElseThrow(() -> new InvalidURLException("The persona with this id doesn't exist"));
        return new ModelAndView("Personas/EditarPersona").addObject("persona", persona);
    }

    @PostMapping("/persona/editar/{idPersona}")
    public String editarPersona(@PathVariable IdValue idPersona, PersonaEditDTO personaEditDTO) {
        iPersonaService.editarPersona(idPersona, PersonaMapper.editionPersonaDTOToPersona(personaEditDTO)).getBody();
        return "redirect:/api/personas";
    }

    @GetMapping("/persona/eliminar/{idPersona}")
    public String eliminarPersona(@PathVariable IdValue idPersona) {
        iPersonaService.eliminarPersona(idPersona);
        return "redirect:/api/personas";
    }

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/relsEventosPersonas")
    public ModelAndView listarRelacionesEventosPersonas(){
        List<RelEventoPersona> relaciones = iRelEventoPersonaService.listarRelEventoPersonas();
        return new ModelAndView("Relaciones/Relaciones").addObject("relaciones", relaciones);
    }

//    @GetMapping("/relEventoPersona")
//    public List<RelEventoPersona> listadoRelEventosPersonas() {
//        return iRelEventoPersonaService.listarRelEventoPersonas();
//    }

    @GetMapping("/relEventoPersona/nueva")
    public ModelAndView nuevaRelacion(Model model){
        List<Evento> eventos = (List<Evento>) iEventoDAO.findAll();
        List<Persona> personas = (List<Persona>) iPersonaDAO.findAll();
        model.addAttribute("eventos", eventos);
        model.addAttribute("personas", personas);
        return new ModelAndView("Relaciones/NuevaRelacion").addObject("relacion", new RelEventoPersona());
    }

    @PostMapping("/relEventoPersona/guardar")
    public String createRelacionEventoPersona(@ModelAttribute RelEventoPersona eventoPersonaDTO){
        Evento eventoNuevo = new Evento();
        eventoNuevo.setIdEvento(eventoPersonaDTO.getEvento().getIdEvento());

        Persona personaNueva = new Persona();
        personaNueva.setIdPersona(eventoPersonaDTO.getPersona().getIdPersona());

        iRelEventoPersonaService.createRelEventoPersona(eventoNuevo, personaNueva);
        return  "redirect:/api/relsEventosPersonas";
    }

    @GetMapping("/relEventoPersona/eliminar/{idEventoPersona}")
    public String eliminarRelEventoPersona(@PathVariable IdValue idEventoPersona){
        iRelEventoPersonaService.eliminarRelEventoPersona(idEventoPersona);
        return "redirect:/api/relsEventosPersonas";
    }
}
