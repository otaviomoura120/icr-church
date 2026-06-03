package com.devhouse.adapters.inbound.http.dto.request;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Introspected
public record UpdateCellChurchRequestDto(
        @NotNull Integer version,
        @NotNull @NotEmpty String name,
        @NotNull Long cellProfileId,
        AddressRequestDto address,
        String weekday,
        String hour
) {
}
