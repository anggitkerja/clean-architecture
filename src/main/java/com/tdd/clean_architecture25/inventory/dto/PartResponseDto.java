package com.tdd.clean_architecture25.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartResponseDto {
    private UUID id;
    private String partNumber;
    private String name;
    private Integer stockQty;
    private Integer minStock;
}
