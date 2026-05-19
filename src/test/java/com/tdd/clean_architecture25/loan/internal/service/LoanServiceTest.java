package com.tdd.clean_architecture25.loan.internal.service;

import com.tdd.clean_architecture25.common.event.LoanCreatedEvent;
import com.tdd.clean_architecture25.loan.dto.LoanRequestDto;
import com.tdd.clean_architecture25.loan.dto.LoanResponseDto;
import com.tdd.clean_architecture25.loan.internal.mapper.LoanMapper;
import com.tdd.clean_architecture25.loan.internal.model.LoanOrder;
import com.tdd.clean_architecture25.loan.internal.repository.LoanOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanOrderRepository loanOrderRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private com.tdd.clean_architecture25.inventory.PartService partService;

    @InjectMocks
    private LoanServiceImpl loanService;

    private LoanOrder loanOrder;
    private LoanResponseDto responseDto;
    private UUID loanId;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        loanOrder = LoanOrder.builder()
                .id(loanId)
                .borrowerName("John Doe")
                .status("PENDING")
                .build();
        
        responseDto = LoanResponseDto.builder()
                .id(loanId)
                .borrowerName("John Doe")
                .status("PENDING")
                .build();
    }

    @Test
    void create_ShouldReduceStockAndSaveOrder() {
        UUID partId = UUID.randomUUID();
        LoanRequestDto.LoanItemRequestDto itemRequest = LoanRequestDto.LoanItemRequestDto.builder()
                .partId(partId)
                .qty(5)
                .build();
        LoanRequestDto request = LoanRequestDto.builder()
                .borrowerName("John Doe")
                .items(List.of(itemRequest))
                .build();

        com.tdd.clean_architecture25.inventory.dto.PartResponseDto part = com.tdd.clean_architecture25.inventory.dto.PartResponseDto.builder()
                .id(partId)
                .stockQty(10)
                .partNumber("PART-001")
                .build();

        when(partService.getById(partId)).thenReturn(part);
        when(loanMapper.toEntity(request)).thenReturn(loanOrder);
        when(loanOrderRepository.save(loanOrder)).thenReturn(loanOrder);
        when(loanMapper.toResponseDto(loanOrder)).thenReturn(responseDto);

        LoanResponseDto result = loanService.create(request);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        verify(eventPublisher, times(1)).publishEvent(any(LoanCreatedEvent.class));
        verify(loanOrderRepository, times(1)).save(loanOrder);
    }

    @Test
    void create_ShouldThrowException_WhenStockInsufficient() {
        UUID partId = UUID.randomUUID();
        LoanRequestDto.LoanItemRequestDto itemRequest = LoanRequestDto.LoanItemRequestDto.builder()
                .partId(partId)
                .qty(10)
                .build();
        LoanRequestDto request = LoanRequestDto.builder()
                .borrowerName("John Doe")
                .items(List.of(itemRequest))
                .build();

        com.tdd.clean_architecture25.inventory.dto.PartResponseDto part = com.tdd.clean_architecture25.inventory.dto.PartResponseDto.builder()
                .id(partId)
                .stockQty(5)
                .partNumber("PART-001")
                .build();

        when(partService.getById(partId)).thenReturn(part);

        assertThrows(com.tdd.clean_architecture25.inventory.exception.InsufficientStockException.class, () -> {
            loanService.create(request);
        });

        verify(loanOrderRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void getAll_ShouldReturnListOfLoans() {
        when(loanOrderRepository.findAll()).thenReturn(List.of(loanOrder));
        when(loanMapper.toResponseDto(loanOrder)).thenReturn(responseDto);

        List<LoanResponseDto> result = loanService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
