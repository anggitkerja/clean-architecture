package com.tdd.clean_architecture25.loan;

import com.tdd.clean_architecture25.common.ApiResponse;
import com.tdd.clean_architecture25.loan.dto.LoanRequestDto;
import com.tdd.clean_architecture25.loan.dto.LoanResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public ApiResponse<List<LoanResponseDto>> getAll() {
        return ApiResponse.success(loanService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<LoanResponseDto> getById(@PathVariable UUID id) {
        return ApiResponse.success(loanService.getById(id));
    }

    @PostMapping
    public ApiResponse<LoanResponseDto> create(@Valid @RequestBody LoanRequestDto request) {
        return ApiResponse.success(loanService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable UUID id, @RequestParam String status) {
        loanService.updateStatus(id, status);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        loanService.delete(id);
        return ApiResponse.success(null);
    }
}
