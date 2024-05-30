package org.api.domain;

import lombok.*;
import java.io.Serializable;

@Data
public class CompraDTO implements Serializable {
    private Long idCompra;

    private String nombre_cliente;

    private Integer numero_entradas;

    private String fecha_compra;

    private Long idEvento;

    private Long idPersona;
}
