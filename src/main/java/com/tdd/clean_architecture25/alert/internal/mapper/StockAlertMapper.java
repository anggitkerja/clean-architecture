package com.tdd.clean_architecture25.alert.internal.mapper;

import com.tdd.clean_architecture25.alert.StockAlertRequestDto;
import com.tdd.clean_architecture25.alert.StockAlertResponseDto;
import com.tdd.clean_architecture25.alert.internal.model.StockAlert;
import org.springframework.stereotype.Component;

@Component
public class StockAlertMapper {

    public StockAlertResponseDto toResponseDto(StockAlert alert) {
        if (alert == null) return null;

        return StockAlertResponseDto.builder()
                .id(alert.getId())
                .partId(alert.getPartId())
                .currentStock(alert.getCurrentStock())
                .threshold(alert.getThreshold())
                .status(alert.getStatus())
                .triggeredAt(alert.getTriggeredAt())
                .build();
    }

    public StockAlert toEntity(StockAlertRequestDto request) {
        if (request == null) return null;

        return StockAlert.builder()
                .partId(request.getPartId())
                .currentStock(request.getCurrentStock())
                .threshold(request.getThreshold())
                .status(request.getStatus() != null ? request.getStatus() : "NEW")
                .build();
    }
}
