package com.devhouse.core.model;

import java.time.Instant;

public class Church {
    private Long id;
    private Integer version;
    private String name;
    private Address address;
    private final Instant createdDate;
    private final Instant updatedDate;

    public Church(Instant createdDate, Instant updatedDate, Address address, String name, Integer version, Long id) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.address = address;
        this.name = name;
        this.version = version;
        this.id = id;
    }
}
