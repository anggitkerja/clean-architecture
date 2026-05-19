package com.tdd.clean_architecture25.alert;



import java.util.List;
import java.util.UUID;

public interface StockAlertService {
    List<StockAlertResponseDto> getAll();
    StockAlertResponseDto getById(UUID id);
    StockAlertResponseDto create(StockAlertRequestDto request);
    void updateStatus(UUID id, String status);
    void delete(UUID id);
}
