package com.devhouse.adapters.inbound.http.dto.response;

import io.micronaut.core.annotation.Introspected;

import java.time.Instant;
import java.time.OffsetTime;

@Introspected
public record CellChurchResponseDto(
        Long id,
        String name,
        Integer version,
        Long cellProfileId,
        String cellProfileName,
        AddressResponseDto address,
        String weekday,
        OffsetTime hour,
        Instant createdAt,
        Instant updatedAt
) {
}
