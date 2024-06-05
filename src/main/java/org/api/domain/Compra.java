package org.api.domain;

import lombok.Data;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "compras")
public class Compra implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    private String nombre_cliente;

    private Integer numero_entradas;

    private String fecha_compra;

    private Long idEvento;

    private Long idPersona;
}
