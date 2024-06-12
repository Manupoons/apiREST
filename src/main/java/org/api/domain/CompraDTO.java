package org.api.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompraDTO implements Serializable {

    private Long idCompra;

    private String nombre_cliente;

    private Integer numero_entradas;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_compra;

    private Long idEvento;

    private Long idPersona;
}
