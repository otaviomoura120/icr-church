package com.devhouse.core.model;

import java.time.Instant;

public class CellProfile {
    private final Long id;
    private final Integer version;
    private final String name;
    private final Instant createdDate;
    private final Instant updatedDate;

    public CellProfile(Long id, Integer version, String name, Instant createdDate, Instant updatedDate) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }
}
