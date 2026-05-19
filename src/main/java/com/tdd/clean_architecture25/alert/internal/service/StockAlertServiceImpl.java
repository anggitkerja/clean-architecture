package com.tdd.clean_architecture25.alert.internal.service;

import com.tdd.clean_architecture25.alert.StockAlertService;
import com.tdd.clean_architecture25.alert.StockAlertRequestDto;
import com.tdd.clean_architecture25.alert.StockAlertResponseDto;
import com.tdd.clean_architecture25.alert.internal.mapper.StockAlertMapper;
import com.tdd.clean_architecture25.alert.internal.model.StockAlert;
import com.tdd.clean_architecture25.alert.internal.repository.StockAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockAlertServiceImpl implements StockAlertService {
    
    private final StockAlertRepository stockAlertRepository;
    private final StockAlertMapper stockAlertMapper;

    @Override
    public List<StockAlertResponseDto> getAll() {
        return stockAlertRepository.findAll().stream()
                .map(stockAlertMapper::toResponseDto)
                .toList();
    }

    @Override
    public StockAlertResponseDto getById(UUID id) {
        return stockAlertRepository.findById(id)
                .map(stockAlertMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("Stock Alert not found"));
    }

    @Override
    @Transactional
    public StockAlertResponseDto create(StockAlertRequestDto request) {
        StockAlert alert = stockAlertMapper.toEntity(request);
        return stockAlertMapper.toResponseDto(stockAlertRepository.save(alert));
    }

    @Override
    @Transactional
    public void updateStatus(UUID id, String status) {
        StockAlert alert = stockAlertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Alert not found"));
        alert.setStatus(status);
        stockAlertRepository.save(alert);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        stockAlertRepository.deleteById(id);
    }
}
