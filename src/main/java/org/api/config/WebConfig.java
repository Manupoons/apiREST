package org.api.config;

import org.api.domain.IdValueConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IdValueConverter idValueConverter;

    public WebConfig(IdValueConverter idValueConverter) {
        this.idValueConverter = idValueConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idValueConverter);
    }
}
