package com.tdd.clean_architecture25.inventory.internal.exception;

import com.tdd.clean_architecture25.common.BaseException;

import java.util.UUID;

public class PartNotFoundException extends BaseException {
    public PartNotFoundException(UUID id) {
        super(
                "IN-404-001",
                "Part not found with id: " + id,
                "Part not found with id: " + id
        );
    }
}
