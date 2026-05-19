package com.tdd.clean_architecture25.loan.internal.listener;

import com.tdd.clean_architecture25.common.event.InventoryProcessedEvent;
import com.tdd.clean_architecture25.loan.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryResultListener {

    private final LoanService loanService;

    @ApplicationModuleListener
    public void onInventoryProcessed(InventoryProcessedEvent event) {
        log.info("Received InventoryProcessedEvent for loanId: {}. Success: {}", event.loanId(), event.success());
        String newStatus = event.success() ? "APPROVED" : "REJECTED";
        loanService.updateStatus(event.loanId(), newStatus);
    }
}
