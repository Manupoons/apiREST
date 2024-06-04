package org.api.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class IdValue {

    @Min(value=1, message = "El id debe ser mayor que 0")
    @PositiveOrZero(message = "El id debe ser positivo o cero")
    @Positive(message = "El id debe ser mayor que 0")
    private final Long value;

    public IdValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
