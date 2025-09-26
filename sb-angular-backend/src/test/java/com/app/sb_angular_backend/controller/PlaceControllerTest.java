package com.app.sb_angular_backend.controller;

import com.app.sb_angular_backend.dto.PlaceRequest;
import com.app.sb_angular_backend.dto.PlaceResponse;
import com.app.sb_angular_backend.model.Employee;
import com.app.sb_angular_backend.service.EmployeeService;
import com.app.sb_angular_backend.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlaceService placeService;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void testAddPlace() throws Exception {
        String description = "Graduate School of Business building at UT Austin";
        String name = "GSB - UT";
        PlaceRequest placeRequest = new PlaceRequest(
                -97.73834346440792,
                30.284193000896142,
                name,
                description
        );
        PlaceResponse response = new PlaceResponse(
                UUID.randomUUID(),
                placeRequest.getLongitude(),
                placeRequest.getLatitude(),
                placeRequest.getName(),
                placeRequest.getDescription(),
                OffsetDateTime.of(2025, 10, 10, 11, 11, 11, 0, ZoneOffset.UTC)
        );

        String testEmail = "fakeemail@gmail.org";
        Employee mockEmployee = new Employee("fake", "fake", testEmail);
        when(employeeService.findByEmail(testEmail)).thenReturn(mockEmployee);

        when(placeService.addNewPlace(any(PlaceRequest.class), any(Employee.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/place")
                        // with() applies a post-processor: function that modifies the request before it's sent
                        // jwt() creates a JWT authentication post-processor that adds auth to test request
                        // method on the JWT post-processor from ^ that lets me configure the JWT token
                        // jwt.claim() adds a claim and sets the value for it
                        .with(jwt().
                                jwt(jwt -> jwt.claim("devSBAuth0/email", testEmail)))
                                        //.claim("scope", "add:places")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(placeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placeId").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description));
    }
}
