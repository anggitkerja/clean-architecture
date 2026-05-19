package com.tdd.clean_architecture25.inventory;

import java.util.UUID;

public record StockBelowThresholdEvent(
    UUID partId,
    Integer currentStock,
    Integer threshold
) {
}
