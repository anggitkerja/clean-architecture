package com.tdd.clean_architecture25.loan.internal.service;

import com.tdd.clean_architecture25.common.event.LoanCreatedEvent;
import com.tdd.clean_architecture25.loan.LoanService;
import com.tdd.clean_architecture25.loan.dto.LoanRequestDto;
import com.tdd.clean_architecture25.loan.dto.LoanResponseDto;
import com.tdd.clean_architecture25.loan.internal.mapper.LoanMapper;
import com.tdd.clean_architecture25.loan.internal.model.LoanOrder;
import com.tdd.clean_architecture25.loan.internal.repository.LoanOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanOrderRepository loanOrderRepository;
    private final LoanMapper loanMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final com.tdd.clean_architecture25.inventory.PartService partService;

    @Override
    public List<LoanResponseDto> getAll() {
        return loanOrderRepository.findAll().stream()
                .map(loanMapper::toResponseDto)
                .toList();
    }

    @Override
    public LoanResponseDto getById(UUID id) {
        return loanOrderRepository.findById(id)
                .map(loanMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("Loan Order not found"));
    }

    @Override
    @Transactional
    public LoanResponseDto create(LoanRequestDto request) {
        // 1. Synchronous Soft-Check (Validation)
        if (request.getItems() != null) {
            for (var item : request.getItems()) {
                var part = partService.getById(item.getPartId());
                if (part.getStockQty() < item.getQty()) {
                    throw new com.tdd.clean_architecture25.inventory.exception.InsufficientStockException(part.getPartNumber());
                }
            }
        }

        // 2. Create Order with PENDING status
        LoanOrder order = loanMapper.toEntity(request);
        order.setStatus("PENDING");
        LoanOrder savedOrder = loanOrderRepository.save(order);

        // 3. Publish event for asynchronous hard-update (Deduction)
        if (request.getItems() != null) {
            List<LoanCreatedEvent.LoanItem> eventItems = request.getItems().stream()
                    .map(item -> new LoanCreatedEvent.LoanItem(item.getPartId(), item.getQty()))
                    .toList();
            eventPublisher.publishEvent(new LoanCreatedEvent(savedOrder.getId(), eventItems));
        }

        return loanMapper.toResponseDto(savedOrder);
    }

    @Override
    @Transactional
    public void updateStatus(UUID id, String status) {
        LoanOrder order = loanOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Order not found"));
        order.setStatus(status);
        loanOrderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        loanOrderRepository.deleteById(id);
    }
}
