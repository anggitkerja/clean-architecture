package com.tdd.clean_architecture25.common.event;

import java.util.List;
import java.util.UUID;

public record LoanCreatedEvent(
    UUID loanId,
    List<LoanItem> items
) {
    public record LoanItem(UUID partId, Integer qty) {}
}
