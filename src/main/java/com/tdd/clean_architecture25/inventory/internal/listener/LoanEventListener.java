package com.tdd.clean_architecture25.inventory.internal.listener;

import com.tdd.clean_architecture25.inventory.PartService;
import com.tdd.clean_architecture25.common.event.InventoryProcessedEvent;
import com.tdd.clean_architecture25.common.event.LoanCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanEventListener {

    private final PartService partService;
    private final org.springframework.context.ApplicationEventPublisher eventPublisher;

    @ApplicationModuleListener
    public void onLoanCreated(LoanCreatedEvent event) {
        log.info("Received LoanCreatedEvent for loanId: {}", event.loanId());
        try {
            for (var item : event.items()) {
                partService.reduceStock(item.partId(), item.qty());
            }
            log.info("Successfully reduced stock for loanId: {}", event.loanId());
            eventPublisher.publishEvent(new InventoryProcessedEvent(
                    event.loanId(), true, "Stock successfully reserved"));
        } catch (Exception e) {
            log.error("Failed to reduce stock for loanId: {}. Error: {}", 
                    event.loanId(), e.getMessage());
            eventPublisher.publishEvent(new InventoryProcessedEvent(
                    event.loanId(), false, e.getMessage()));
        }
    }
}
