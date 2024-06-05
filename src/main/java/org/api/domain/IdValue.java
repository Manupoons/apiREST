package org.api.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class IdValue {

    @Min(value=1, message = "El id debe ser mayor que 0")
    @Positive(message = "El id no puede ser negativo")
    private final Long value;

    public IdValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
