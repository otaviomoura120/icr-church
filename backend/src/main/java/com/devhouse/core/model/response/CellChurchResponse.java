package com.devhouse.core.model.response;

import com.devhouse.core.model.Address;
import com.devhouse.core.model.CellProfile;

import java.time.Instant;
import java.time.OffsetTime;

public record CellChurchResponse(Long id, String name, Integer version, CellProfile cellProfile, Address address, String weekday, OffsetTime hour, Instant createdAt, Instant updatedAt) {
}
