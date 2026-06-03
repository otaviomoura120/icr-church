package com.devhouse.adapters.outbound.repositoryJpa.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.OffsetTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cells_church")
public class CellChurchEntityJpa {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "cell_profile_id")
    private CellProfileEntityJpa cellProfile;

    private AddressEntityJpa address;

    private String name;

    private String weekday;

    private OffsetTime hour;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public void setVersion(Integer version) {
        if (!Objects.equals(version, this.version)) {
            throw new OptimisticLockException("Error optimistic locking Cell Church", new Exception());
        }
    }
}

