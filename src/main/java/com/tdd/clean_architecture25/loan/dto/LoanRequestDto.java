package com.tdd.clean_architecture25.loan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {

    @NotBlank(message = "Borrower name is required")
    private String borrowerName;

    @NotEmpty(message = "Loan items cannot be empty")
    @Valid
    private List<LoanItemRequestDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanItemRequestDto {
        @NotNull(message = "Part ID is required")
        private UUID partId;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer qty;
    }
}
