package com.devhouse.adapters.inbound.http.dto.request;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Introspected
public record UpdateFamilyRequestDto(
        @NotNull Integer version,
        @NotNull @NotEmpty String name,
        AddressRequestDto address
) {
}
