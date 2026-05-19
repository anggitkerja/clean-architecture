package com.tdd.clean_architecture25.loan;

import com.tdd.clean_architecture25.loan.dto.LoanRequestDto;
import com.tdd.clean_architecture25.loan.dto.LoanResponseDto;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    List<LoanResponseDto> getAll();
    LoanResponseDto getById(UUID id);
    LoanResponseDto create(LoanRequestDto request);
    void updateStatus(UUID id, String status);
    void delete(UUID id);
}
