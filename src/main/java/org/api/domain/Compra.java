package org.api.domain;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @NotNull(message = "Numero entradas no puede estar vacio")
    @JsonProperty(required = true)
    private Integer numero_entradas;

    @NotEmpty(message = "La fecha no puede estar vacia")
    @JsonProperty(required = true)
    private String fecha_compra;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;
}
