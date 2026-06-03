package com.devhouse.adapters.outbound.repositoryJpa.entities;

import com.devhouse.core.model.CellProfile;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cell_profiles")
public class CellProfileEntityJpa {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public void setVersion(Integer version) {
        if (!Objects.equals(version, this.version)) {
            throw new OptimisticLockException("Error optimistic locking CellProfile", new Exception());
        }
    }

    public static CellProfileEntityJpa from(CellProfile cellProfile) {
        if (cellProfile == null) return null;
        CellProfileEntityJpa entity = new CellProfileEntityJpa();
        entity.setId(cellProfile.getId());
        entity.version = cellProfile.getVersion();
        entity.setName(cellProfile.getName());
        entity.setCreatedAt(cellProfile.getCreatedDate());
        entity.setUpdatedAt(cellProfile.getUpdatedDate());
        return entity;
    }

    public static CellProfileEntityJpa stub(Long id) {
        if (id == null) return null;
        CellProfileEntityJpa entity = new CellProfileEntityJpa();
        entity.setId(id);
        return entity;
    }

    public CellProfile toModel() {
        return new CellProfile(id, version, name, createdAt, updatedAt);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
