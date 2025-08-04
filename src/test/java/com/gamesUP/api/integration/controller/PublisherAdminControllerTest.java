package com.gamesup.api.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesup.api.config.WebMvcTestNoSecurity;
import com.gamesup.api.controller.PublisherAdminController;
import com.gamesup.api.dto.publisher.PublisherDto;
import com.gamesup.api.dto.publisher.CreatePublisherDto;
import com.gamesup.api.dto.publisher.UpdatePublisherDto;
import com.gamesup.api.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTestNoSecurity(controllers = PublisherAdminController.class)
class PublisherAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPublisher_shouldReturnCreated() throws Exception {
        CreatePublisherDto dto = new CreatePublisherDto("New Publisher");
        PublisherDto response = new PublisherDto(1L, "New Publisher");

        Mockito.when(publisherService.createPublisher(any(CreatePublisherDto.class))).thenReturn(response);

        mockMvc.perform(post("/admin/publishers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("New Publisher"));
    }

    @Test
    void updatePublisher_shouldReturnUpdated() throws Exception {
        UpdatePublisherDto dto = new UpdatePublisherDto("Updated Publisher");
        PublisherDto response = new PublisherDto(1L, "Updated Publisher");

        Mockito.when(publisherService.updatePublisher(Mockito.eq(1L), any(UpdatePublisherDto.class))).thenReturn(response);

        mockMvc.perform(put("/admin/publishers/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Publisher"));
    }

    @Test
    void deletePublisher_shouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/admin/publishers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Publisher deleted successfully"));
    }
}
