package com.tdd.clean_architecture25.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartRequestDto {

    @NotBlank(message = "Part number is required")
    @Size(max = 20, message = "Part number must be less than 20 characters")
    private String partNumber;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity must be at least 0")
    private Integer stockQty;

    @NotNull(message = "Minimum stock is required")
    @Min(value = 0, message = "Minimum stock must be at least 0")
    private Integer minStock;
}
