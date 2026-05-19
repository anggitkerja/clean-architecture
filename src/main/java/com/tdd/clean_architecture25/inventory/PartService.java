package com.tdd.clean_architecture25.inventory;

import com.tdd.clean_architecture25.inventory.dto.PartRequestDto;
import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;

import java.util.List;
import java.util.UUID;

public interface PartService {
    List<PartResponseDto> getAll();
    PartResponseDto getById(UUID id);
    PartResponseDto create(PartRequestDto request);
    PartResponseDto update(UUID id, PartRequestDto request);
    void delete(UUID id);
    void reduceStock(UUID id, Integer qty);
}
