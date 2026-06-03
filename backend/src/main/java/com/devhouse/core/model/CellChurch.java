package com.devhouse.core.model;

import com.devhouse.core.model.response.CellChurchResponse;

import java.time.Instant;
import java.time.OffsetTime;

public class CellChurch {
    private final Long id;
    private final Integer version;
    private final CellProfile cellProfile;
    private final Address address;
    private final String name;
    private final String weekday;
    private final OffsetTime hour;
    private final Instant createdDate;
    private final Instant updatedDate;

    public CellChurch(Long id, Integer version, CellProfile cellProfile, Address address, String name, String weekday, OffsetTime hour, Instant createdDate, Instant updatedDate) {
        this.id = id;
        this.version = version;
        this.cellProfile = cellProfile;
        this.address = address;
        this.name = name;
        this.weekday = weekday;
        this.hour = hour;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public CellChurch(String name, CellProfile cellProfile, Address address, String weekday, OffsetTime hour) {
        this.id = null;
        this.version = null;
        this.name = name;
        this.cellProfile = cellProfile;
        this.address = address;
        this.weekday = weekday;
        this.hour = hour;
        this.createdDate = Instant.now();
        this.updatedDate = Instant.now();
    }

    public static CellChurch stub(Long id) {
        return new CellChurch(id, null, null, null, null, null, null, null, null);
    }

    public CellChurchResponse toResponse() {
        return new CellChurchResponse(id, name, version, cellProfile, address, weekday, hour, createdDate, updatedDate);
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public CellProfile getCellProfile() {
        return cellProfile;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getWeekday() {
        return weekday;
    }

    public OffsetTime getHour() {
        return hour;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }
}
