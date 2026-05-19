package com.tdd.clean_architecture25.loan.internal.mapper;

import com.tdd.clean_architecture25.loan.dto.LoanRequestDto;
import com.tdd.clean_architecture25.loan.dto.LoanResponseDto;
import com.tdd.clean_architecture25.loan.internal.model.LoanItem;
import com.tdd.clean_architecture25.loan.internal.model.LoanOrder;
import org.springframework.stereotype.Component;


@Component
public class LoanMapper {

    public LoanResponseDto toResponseDto(LoanOrder order) {
        if (order == null) return null;

        return LoanResponseDto.builder()
                .id(order.getId())
                .borrowerName(order.getBorrowerName())
                .loanDate(order.getLoanDate())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(this::toItemResponseDto)
                        .toList())
                .build();
    }

    private LoanResponseDto.LoanItemResponseDto toItemResponseDto(LoanItem item) {
        return LoanResponseDto.LoanItemResponseDto.builder()
                .id(item.getId())
                .partId(item.getPartId())
                .qty(item.getQty())
                .build();
    }

    public LoanOrder toEntity(LoanRequestDto request) {
        if (request == null) return null;

        LoanOrder order = LoanOrder.builder()
                .borrowerName(request.getBorrowerName())
                .build();

        if (request.getItems() != null) {
            request.getItems().forEach(itemDto -> {
                LoanItem item = LoanItem.builder()
                        .partId(itemDto.getPartId())
                        .qty(itemDto.getQty())
                        .build();
                order.addItem(item);
            });
        }

        return order;
    }
}
