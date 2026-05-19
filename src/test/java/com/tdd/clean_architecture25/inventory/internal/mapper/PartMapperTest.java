package com.tdd.clean_architecture25.inventory.internal.mapper;

import com.tdd.clean_architecture25.inventory.dto.PartRequestDto;
import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;
import com.tdd.clean_architecture25.inventory.internal.model.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PartMapperTest {

    private PartMapper partMapper;

    @BeforeEach
    void setUp() {
        partMapper = new PartMapper();
    }

    @Test
    void toResponseDto_ShouldMapCorrectiy() {
        UUID id = UUID.randomUUID();
        Part part = Part.builder()
                .id(id)
                .partNumber("PART-001")
                .name("Test Part")
                .stockQty(10)
                .minStock(2)
                .build();

        PartResponseDto result = partMapper.toResponseDto(part);

        assertNotNull(result);
        assertEquals(part.getId(), result.getId());
        assertEquals(part.getPartNumber(), result.getPartNumber());
        assertEquals(part.getName(), result.getName());
        assertEquals(part.getStockQty(), result.getStockQty());
        assertEquals(part.getMinStock(), result.getMinStock());
    }

    @Test
    void toResponseDto_WhenNull_ShouldReturnNull() {
        assertNull(partMapper.toResponseDto(null));
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        PartRequestDto request = PartRequestDto.builder()
                .partNumber("PART-001")
                .name("Test Part")
                .stockQty(10)
                .minStock(2)
                .build();

        Part result = partMapper.toEntity(request);

        assertNotNull(result);
        assertEquals(request.getPartNumber(), result.getPartNumber());
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getStockQty(), result.getStockQty());
        assertEquals(request.getMinStock(), result.getMinStock());
        assertNull(result.getId());
    }

    @Test
    void updateEntity_ShouldUpdateFields() {
        Part part = Part.builder()
                .partNumber("OLD-001")
                .name("Old Name")
                .stockQty(5)
                .minStock(1)
                .build();

        PartRequestDto request = PartRequestDto.builder()
                .partNumber("NEW-001")
                .name("New Name")
                .stockQty(20)
                .minStock(5)
                .build();

        partMapper.updateEntity(part, request);

        assertEquals("NEW-001", part.getPartNumber());
        assertEquals("New Name", part.getName());
        assertEquals(20, part.getStockQty());
        assertEquals(5, part.getMinStock());
    }
}
