package com.tdd.clean_architecture25.common.event;

import java.util.UUID;

public record InventoryProcessedEvent(
    UUID loanId,
    boolean success,
    String message
) {}
