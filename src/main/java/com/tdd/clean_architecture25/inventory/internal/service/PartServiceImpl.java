package com.tdd.clean_architecture25.inventory.internal.service;

import com.tdd.clean_architecture25.inventory.PartService;
import com.tdd.clean_architecture25.inventory.StockBelowThresholdEvent;
import com.tdd.clean_architecture25.inventory.dto.PartRequestDto;
import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;
import com.tdd.clean_architecture25.inventory.exception.InsufficientStockException;
import com.tdd.clean_architecture25.inventory.internal.exception.PartNotFoundException;
import com.tdd.clean_architecture25.inventory.internal.mapper.PartMapper;
import com.tdd.clean_architecture25.inventory.internal.model.Part;
import com.tdd.clean_architecture25.inventory.internal.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final PartMapper partMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<PartResponseDto> getAll() {
        return partRepository.findAll().stream()
                .map(partMapper::toResponseDto)
                .toList();
    }

    @Override
    public PartResponseDto getById(UUID id) {
        return partRepository.findById(id)
                .map(partMapper::toResponseDto)
                .orElseThrow(() -> new PartNotFoundException(id));
    }

    @Override
    public PartResponseDto create(PartRequestDto request) {
        Part part = partMapper.toEntity(request);
        Part savedPart = partRepository.save(part);
        return partMapper.toResponseDto(savedPart);
    }

    @Override
    public PartResponseDto update(UUID id, PartRequestDto request) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));

        partMapper.updateEntity(part, request);
        Part updatedPart = partRepository.save(part);
        return partMapper.toResponseDto(updatedPart);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!partRepository.existsById(id)) {
            throw new PartNotFoundException(id);
        }
        partRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void reduceStock(UUID id, Integer qty) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));

        if (qty > part.getStockQty()) {
            throw new InsufficientStockException(part.getPartNumber());
        }

        part.setStockQty(part.getStockQty() - qty);
        partRepository.save(part);

        if (part.getStockQty() < part.getMinStock()) {
            eventPublisher.publishEvent(new StockBelowThresholdEvent(
                    part.getId(), 
                    part.getStockQty(), 
                    part.getMinStock()
            ));
        }
    }
}
