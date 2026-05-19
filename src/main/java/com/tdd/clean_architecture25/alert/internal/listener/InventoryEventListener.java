package com.tdd.clean_architecture25.alert.internal.listener;

import com.tdd.clean_architecture25.alert.StockAlertRequestDto;
import com.tdd.clean_architecture25.alert.StockAlertService;
import com.tdd.clean_architecture25.inventory.StockBelowThresholdEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventListener {

    private final StockAlertService stockAlertService;

    @ApplicationModuleListener
    public void onStockBelowThreshold(StockBelowThresholdEvent event) {
        log.info("Received StockBelowThresholdEvent for partId: {}", event.partId());
        
        stockAlertService.create(StockAlertRequestDto.builder()
                .partId(event.partId())
                .currentStock(event.currentStock())
                .threshold(event.threshold())
                .status("NEW")
                .build());
    }
}
