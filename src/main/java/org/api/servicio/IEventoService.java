package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.Evento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEventoService {

    List<Evento> listarEventos();

    ResponseEntity<Evento> nuevoEvento(Evento evento);

    ResponseEntity<Evento> editarEvento(Long id, Evento evento);

    List<Compra> listadoCompraPorEvento(Long id);

    void eliminarEvento(Long id);
}
