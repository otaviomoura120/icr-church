package com.devhouse.adapters.inbound.http.dto.request;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Introspected
public record UpdateMemberRequestDto(
        @NotNull Integer version,
        @NotNull @NotEmpty String name,
        Long familyId,
        Long cellChurchId,
        String nickname,
        String ministry,
        String email,
        String phoneNumber,
        String genre,
        String maritalStatus,
        Integer familyOrder,
        Instant birthdate,
        Instant presentationDate,
        String observation,
        boolean active,
        boolean isBaptized
) {}
