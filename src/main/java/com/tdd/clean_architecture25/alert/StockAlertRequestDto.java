package com.tdd.clean_architecture25.alert;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAlertRequestDto {
    
    @NotNull(message = "Part ID is required")
    private UUID partId;

    @NotNull(message = "Current stock is required")
    @Min(value = 0, message = "Current stock cannot be negative")
    private Integer currentStock;

    @NotNull(message = "Threshold is required")
    @Min(value = 0, message = "Threshold cannot be negative")
    private Integer threshold;

    private String status;
}
