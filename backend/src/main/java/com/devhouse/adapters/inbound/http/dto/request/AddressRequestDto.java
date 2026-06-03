package com.devhouse.adapters.inbound.http.dto.request;

import com.devhouse.core.model.Address;
import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//@Serdeable
@Introspected
public record AddressRequestDto(
        @NotNull @NotEmpty String street,
        @NotNull @NotEmpty String zipCode,
        @NotNull @NotEmpty String country,
        @NotNull @NotEmpty String neighborhood,
        @NotNull @NotEmpty String state,
        @NotNull @NotEmpty String city
) {
    public Address createModel() {
        return new Address(street, zipCode, country, neighborhood, state, city);
    }
}
