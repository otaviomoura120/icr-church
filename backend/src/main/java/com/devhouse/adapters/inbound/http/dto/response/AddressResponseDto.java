package com.devhouse.adapters.inbound.http.dto.response;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record AddressResponseDto(
        String street,
        String zipCode,
        String country,
        String state,
        String city,
        String neighborhood
) {
}
