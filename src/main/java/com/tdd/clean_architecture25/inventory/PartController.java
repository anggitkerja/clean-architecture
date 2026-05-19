package com.tdd.clean_architecture25.inventory;

import com.tdd.clean_architecture25.common.ApiResponse;
import com.tdd.clean_architecture25.inventory.dto.PartRequestDto;
import com.tdd.clean_architecture25.inventory.dto.PartResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parts")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    @GetMapping
    public ApiResponse<List<PartResponseDto>> getAll() {
        return ApiResponse.success(partService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PartResponseDto> getById(@PathVariable UUID id) {
        return ApiResponse.success(partService.getById(id));
    }

    @PostMapping
    public ApiResponse<PartResponseDto> create(@Valid @RequestBody PartRequestDto request) {
        return ApiResponse.success(partService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PartResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PartRequestDto request) {
        return ApiResponse.success(partService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        partService.delete(id);
        return ApiResponse.success(null);
    }
}
