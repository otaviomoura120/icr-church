package com.devhouse.adapters.inbound.http.dto.response;

import io.micronaut.core.annotation.Introspected;

import java.time.Instant;

@Introspected
public record CreateFamilyResponseDto(
        Long id,
        String name,
        Integer version,
        AddressResponseDto address,
        Instant createdAt,
        Instant updatedAt
) {
}
