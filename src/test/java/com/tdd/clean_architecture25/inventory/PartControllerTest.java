package com.tdd.clean_architecture25.inventory;

import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(PartController.class)
@ActiveProfiles("test")
class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PartService partService;

    @Test
    void getAll_ShouldReturnApiResponseWithList() throws Exception {
        UUID id = UUID.randomUUID();
        PartResponseDto dto = PartResponseDto.builder()
                .id(id)
                .partNumber("PART-001")
                .name("Gear")
                .build();

        when(partService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/parts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_schema.error_code").value("CC-00-000"))
                .andExpect(jsonPath("$.error_schema.error_message.english").value("Success"))
                .andExpect(jsonPath("$.output_schema[0].partNumber").value("PART-001"))
                .andExpect(jsonPath("$.output_schema[0].name").value("Gear"));
    }

    @Test
    void getById_ShouldReturnApiResponseWithObject() throws Exception {
        UUID id = UUID.randomUUID();
        PartResponseDto dto = PartResponseDto.builder()
                .id(id)
                .partNumber("PART-001")
                .name("Gear")
                .build();

        when(partService.getById(id)).thenReturn(dto);

        mockMvc.perform(get("/api/parts/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_schema.error_code").value("CC-00-000"))
                .andExpect(jsonPath("$.output_schema.id").value(id.toString()));
    }
}
