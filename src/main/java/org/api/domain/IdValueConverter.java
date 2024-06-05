package org.api.domain;

import org.api.exception.InvalidIdException;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class IdValueConverter implements Converter<String, IdValue> {

    @Override
    public IdValue convert(String source) {
        try {
            if (source.contains(".") || source.contains(",")) {
                throw new InvalidIdException("El id debe ser un numero entero");
            }
            Long value = Long.valueOf(source);
            if (value < 1) {
                throw new InvalidIdException("El id debe ser mayor que 0");
            }
            if (value > 1000000) {
                throw new InvalidIdException("El id no puede ser tan grande");
            }
            return new IdValue(value);
        } catch (NumberFormatException e) {
            throw new InvalidIdException("Invalid id format");
        }
    }
}
