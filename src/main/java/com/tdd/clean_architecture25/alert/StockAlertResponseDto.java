package com.tdd.clean_architecture25.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAlertResponseDto {
    private UUID id;
    private UUID partId;
    private Integer currentStock;
    private Integer threshold;
    private String status;
    private OffsetDateTime triggeredAt;
}
