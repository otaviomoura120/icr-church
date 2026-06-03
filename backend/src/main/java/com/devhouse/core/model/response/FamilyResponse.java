package com.devhouse.core.model.response;

import com.devhouse.core.model.Address;

import java.time.Instant;

public record FamilyResponse(Long id, String name, Integer version, Address address, Instant createdAt, Instant updatedAt) {
}
