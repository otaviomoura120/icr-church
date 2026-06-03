package com.devhouse.adapters.inbound.http.dto.response;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CreateFamilyResponseDto(
        Long id,
        String name
) {
}
