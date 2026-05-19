package com.tdd.clean_architecture25.inventory.exception;

import com.tdd.clean_architecture25.common.BaseException;

public class InsufficientStockException extends BaseException {
    public InsufficientStockException(String partNumber) {
        super(
            "STOCK-400-001",
            "Insufficient stock for part: " + partNumber,
            "Stok tidak mencukupi untuk part: " + partNumber
        );
    }
}
