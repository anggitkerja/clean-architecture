package com.tdd.clean_architecture25.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDto {
    private UUID id;
    private String borrowerName;
    private OffsetDateTime loanDate;
    private String status;
    private OffsetDateTime createdAt;
    private List<LoanItemResponseDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanItemResponseDto {
        private UUID id;
        private UUID partId;
        private Integer qty;
    }
}
