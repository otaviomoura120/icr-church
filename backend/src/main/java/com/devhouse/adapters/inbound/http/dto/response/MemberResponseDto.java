package com.devhouse.adapters.inbound.http.dto.response;

import java.time.Instant;

public record MemberResponseDto(
        Long id,
        Integer version,
        Long familyId,
        Long cellChurchId,
        String name,
        String ministry,
        String nickname,
        String profilePhoto,
        String observation,
        Integer familyOrder,
        Instant birthdate,
        Instant presentationDate,
        String email,
        String phoneNumber,
        boolean active,
        boolean isBaptized,
        String genre,
        String maritalStatus,
        Instant createdAt,
        Instant updatedAt
) {}
