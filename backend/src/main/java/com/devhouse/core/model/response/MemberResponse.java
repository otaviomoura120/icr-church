package com.devhouse.core.model.response;

import java.time.Instant;

public record MemberResponse(
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
        Instant createdDate,
        Instant updatedDate
) {}
