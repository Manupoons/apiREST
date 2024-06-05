package org.api.servicio;

import org.api.domain.Compra;
import org.api.domain.Evento;
import org.api.domain.IdValue;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEventoService {

    List<Evento> listarEventos();

    ResponseEntity<Evento> nuevoEvento(Evento evento);

    ResponseEntity<Evento> editarEvento(IdValue id, Evento evento);

    List<Compra> listadoCompraPorEvento(IdValue id);

    List<Evento> listadoEventosPorPersona(IdValue id);

    void eliminarEvento(IdValue id);
}
