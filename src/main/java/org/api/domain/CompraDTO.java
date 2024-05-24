package org.api.domain;

import lombok.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Data
public class CompraDTO implements Serializable {
    private Long idCompra;

    @NotEmpty(message = "El nombre del cliente no puede estar vacio")
    private String nombre_cliente;

    @NotNull(message = "El numero de entradas no puede estar vacio")
    private Integer numero_entradas;

    private String fecha_compra;

    private Evento evento;
}
