package com.devhouse.core.model;

import com.devhouse.core.model.response.FamilyResponse;

import java.time.Instant;

public class Family {
    private Long id;
    private Integer version;
    private final String name;
    private final Address address;
    private final Instant createdDate;
    private final Instant updatedDate;

    public Family(Long id, String name, Address address, Integer version, Instant createdDate, Instant updatedDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.version = version;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Family(String name, Address address) {
        this.name = name;
        this.address = address;
        this.createdDate = Instant.now();
        this.updatedDate = Instant.now();
    }

    public FamilyResponse toResponse() {
        return new FamilyResponse(id, name);
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

    public Address getAddress() {
        return address;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }
}
