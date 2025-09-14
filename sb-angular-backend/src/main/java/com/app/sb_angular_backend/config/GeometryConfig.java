package com.app.sb_angular_backend.config;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// registers a bean that can be injected wherever needed.
@Configuration  // Marks this class as a source of bean definitions for the Spring context
public class GeometryConfig {

    // when this factory is needed, use this method to make it and then keep it around to not have to rebuild it
    @Bean
    public GeometryFactory geometryFactory() {
        // PrecisionModel defines how coordinates are handled (ie num decimals)
        return new GeometryFactory(new PrecisionModel(), 4326);
    }
}