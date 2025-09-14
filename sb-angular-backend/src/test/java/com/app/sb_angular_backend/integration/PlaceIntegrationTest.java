package com.app.sb_angular_backend.integration;

import com.app.sb_angular_backend.model.Place;
import com.app.sb_angular_backend.repository.PlaceRepository;
import com.app.sb_angular_backend.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class PlaceIntegrationTest {
    private static final DockerImageName image = DockerImageName
            .parse("postgis/postgis:15-3.3")
            .asCompatibleSubstituteFor("postgres");

    @Container
    static PostgreSQLContainer<?> postgisContainer = new PostgreSQLContainer<>(image)
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgisContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgisContainer::getUsername);
        registry.add("spring.datasource.password", postgisContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private GeometryFactory geometryFactory;

    @Autowired
    private PlaceService placeService;

    @BeforeEach
    void setup() {
        placeRepository.deleteAll();
    }

    @Test
    void testNearestEndpoint() throws Exception {
        placeRepository.save(new Place("test_name", "test_description", geometryFactory.createPoint(new Coordinate(-97.11, 30.11))));
        mockMvc.perform(get("/api/v1/place/nearest")
                        .param("longitude", "-97.109")
                        .param("latitude", "30.109"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("test_name"))
                .andExpect(jsonPath("$[0].description").value("test_description"))
                .andExpect(jsonPath("$[0].longitude").value(-97.11))
                .andExpect(jsonPath("$[0].latitude").value(30.11));
    }

}
