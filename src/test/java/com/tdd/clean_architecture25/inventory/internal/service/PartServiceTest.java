package com.tdd.clean_architecture25.inventory.internal.service;

import com.tdd.clean_architecture25.inventory.StockBelowThresholdEvent;
import com.tdd.clean_architecture25.inventory.dto.PartRequestDto;
import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;
import com.tdd.clean_architecture25.inventory.exception.InsufficientStockException;
import com.tdd.clean_architecture25.inventory.internal.exception.PartNotFoundException;
import com.tdd.clean_architecture25.inventory.internal.mapper.PartMapper;
import com.tdd.clean_architecture25.inventory.internal.model.Part;
import com.tdd.clean_architecture25.inventory.internal.repository.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {

    @Mock
    private PartRepository partRepository;

    @Mock
    private PartMapper partMapper;

    @Mock
    private org.springframework.context.ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PartServiceImpl partService;

    private Part part;
    private PartResponseDto responseDto;
    private UUID partId;

    @BeforeEach
    void setUp() {
        partId = UUID.randomUUID();
        part = Part.builder()
                .id(partId)
                .partNumber("PART-001")
                .name("Test Part")
                .build();
        
        responseDto = PartResponseDto.builder()
                .id(partId)
                .partNumber("PART-001")
                .name("Test Part")
                .build();
    }

    @Test
    void getAll_ShouldReturnListOfParts() {
        when(partRepository.findAll()).thenReturn(List.of(part));
        when(partMapper.toResponseDto(part)).thenReturn(responseDto);

        List<PartResponseDto> result = partService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(partRepository, times(1)).findAll();
    }

    @Test
    void getById_WhenFound_ShouldReturnPart() {
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));
        when(partMapper.toResponseDto(part)).thenReturn(responseDto);

        PartResponseDto result = partService.getById(partId);

        assertNotNull(result);
        assertEquals(partId, result.getId());
    }

    @Test
    void getById_WhenNotFound_ShouldThrowException() {
        when(partRepository.findById(partId)).thenReturn(Optional.empty());

        assertThrows(PartNotFoundException.class, () -> partService.getById(partId));
    }

    @Test
    void create_ShouldSaveAndReturnPart() {
        PartRequestDto request = PartRequestDto.builder()
                .partNumber("PART-001")
                .name("Test Part")
                .build();

        when(partMapper.toEntity(request)).thenReturn(part);
        when(partRepository.save(part)).thenReturn(part);
        when(partMapper.toResponseDto(part)).thenReturn(responseDto);

        PartResponseDto result = partService.create(request);

        assertNotNull(result);
        assertEquals("PART-001", result.getPartNumber());
        verify(partRepository, times(1)).save(any(Part.class));
    }

    @Test
    void update_WhenFound_ShouldUpdateAndReturnPart() {
        PartRequestDto request = PartRequestDto.builder()
                .partNumber("PART-NEW")
                .name("New Name")
                .build();

        when(partRepository.findById(partId)).thenReturn(Optional.of(part));
        when(partRepository.save(part)).thenReturn(part);
        when(partMapper.toResponseDto(part)).thenReturn(responseDto);

        PartResponseDto result = partService.update(partId, request);

        assertNotNull(result);
        verify(partMapper, times(1)).updateEntity(part, request);
        verify(partRepository, times(1)).save(part);
    }

    @Test
    void delete_WhenExists_ShouldDelete() {
        when(partRepository.existsById(partId)).thenReturn(true);
        
        partService.delete(partId);

        verify(partRepository, times(1)).deleteById(partId);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowException() {
        when(partRepository.existsById(partId)).thenReturn(false);

        assertThrows(PartNotFoundException.class, () -> partService.delete(partId));
    }

    @Test
    void reduceStock_WhenStockIsSufficient_ShouldReduceStock() {
        part.setStockQty(10);
        part.setMinStock(5);
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));

        partService.reduceStock(partId, 3);

        assertEquals(7, part.getStockQty());
        verify(partRepository, times(1)).save(part);
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void reduceStock_WhenStockIsInsufficient_ShouldThrowException() {
        part.setStockQty(5);
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));

        assertThrows(InsufficientStockException.class, () -> partService.reduceStock(partId, 10));
        verify(partRepository, never()).save(any());
    }

    @Test
    void reduceStock_WhenStockFallsBelowMin_ShouldTriggerAlert() {
        part.setStockQty(10);
        part.setMinStock(5);
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));

        partService.reduceStock(partId, 7); // Remaining: 3 < 5

        assertEquals(3, part.getStockQty());
        verify(partRepository, times(1)).save(part);
        verify(eventPublisher, times(1)).publishEvent(any(StockBelowThresholdEvent.class));
    }
}
