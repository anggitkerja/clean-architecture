package com.tdd.clean_architecture25.inventory.internal.mapper;

import com.tdd.clean_architecture25.inventory.dto.PartRequestDto;
import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;
import com.tdd.clean_architecture25.inventory.internal.model.Part;
import org.springframework.stereotype.Component;

@Component
public class PartMapper {

    public PartResponseDto toResponseDto(Part part) {
        if (part == null) return null;
        
        return PartResponseDto.builder()
                .id(part.getId())
                .partNumber(part.getPartNumber())
                .name(part.getName())
                .stockQty(part.getStockQty())
                .minStock(part.getMinStock())
                .build();
    }

    public Part toEntity(PartRequestDto request) {
        if (request == null) return null;

        return Part.builder()
                .partNumber(request.getPartNumber())
                .name(request.getName())
                .stockQty(request.getStockQty())
                .minStock(request.getMinStock())
                .build();
    }

    public void updateEntity(Part part, PartRequestDto request) {
        if (request == null) return;

        part.setPartNumber(request.getPartNumber());
        part.setName(request.getName());
        part.setStockQty(request.getStockQty());
        part.setMinStock(request.getMinStock());
    }
}
