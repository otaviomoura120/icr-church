package com.devhouse.adapters.inbound.http.dto.response;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
public record CellChurchPageResponseDto(
        List<CellChurchResponseDto> content,
        long totalElements,
        int totalPages,
        int page,
        int size
) {
}
