package com.app.sb_angular_backend.controller;

import com.app.sb_angular_backend.dto.PlaceRequest;
import com.app.sb_angular_backend.dto.PlaceResponse;
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
import java.util.UUID;

@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlaceService placeService;

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
                placeRequest.getDescription()
        );

        when(placeService.addNewPlace(any(PlaceRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/place")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(placeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placeId").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description));
    }
}
