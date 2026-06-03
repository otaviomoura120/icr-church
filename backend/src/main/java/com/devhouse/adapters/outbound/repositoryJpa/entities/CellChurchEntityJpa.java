package com.devhouse.adapters.outbound.repositoryJpa.entities;

import com.devhouse.core.model.Address;
import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.CellProfile;
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

    public static CellChurchEntityJpa stub(Long id) {
        if (id == null) return null;
        CellChurchEntityJpa entity = new CellChurchEntityJpa();
        entity.setId(id);
        return entity;
    }

    public static CellChurchEntityJpa from(CellChurch cellChurch) {
        if (cellChurch == null) return null;
        CellChurchEntityJpa entity = new CellChurchEntityJpa();
        entity.setId(cellChurch.getId());
        entity.version = cellChurch.getVersion();
        entity.setName(cellChurch.getName());
        entity.setWeekday(cellChurch.getWeekday());
        entity.setHour(cellChurch.getHour());
        entity.setCreatedAt(cellChurch.getCreatedDate());
        entity.setUpdatedAt(cellChurch.getUpdatedDate());
        if (cellChurch.getCellProfile() != null) {
            entity.setCellProfile(CellProfileEntityJpa.stub(cellChurch.getCellProfile().getId()));
        }
        if (cellChurch.getAddress() != null) {
            entity.setAddress(AddressEntityJpa.from(cellChurch.getAddress()));
        }
        return entity;
    }

    public CellChurch toModel() {
        CellProfile cellProfileModel = cellProfile != null ? cellProfile.toModel() : null;
        Address addressModel = address != null ? address.toModel() : null;
        return new CellChurch(id, version, cellProfileModel, addressModel, name, weekday, hour, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public CellProfileEntityJpa getCellProfile() {
        return cellProfile;
    }

    public void setCellProfile(CellProfileEntityJpa cellProfile) {
        this.cellProfile = cellProfile;
    }

    public AddressEntityJpa getAddress() {
        return address;
    }

    public void setAddress(AddressEntityJpa address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public OffsetTime getHour() {
        return hour;
    }

    public void setHour(OffsetTime hour) {
        this.hour = hour;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
