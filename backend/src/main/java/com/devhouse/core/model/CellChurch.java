package com.devhouse.core.model;

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
}
