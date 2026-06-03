package com.devhouse.adapters.outbound.repositoryJpa.entities;

import com.devhouse.core.model.Address;
import com.devhouse.core.model.Family;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "families")
public class FamilyEntityJpa {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private String name;

    private AddressEntityJpa address;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public void setVersion(Integer version) {
        if (!Objects.equals(version, this.version)) {
            throw new OptimisticLockException("Error optimistic locking Family", new Exception());
        }
    }

    public static FamilyEntityJpa from(Family family) {
        if (family == null) {
            return null;
        }
        
        FamilyEntityJpa entity = new FamilyEntityJpa();
        entity.setId(family.getId());
        entity.setName(family.getName());
        entity.setVersion(family.getVersion());
        entity.setCreatedAt(family.getCreatedDate());
        entity.setUpdatedAt(family.getUpdatedDate());
        
        if (family.getAddress() != null) {
            entity.setAddress(AddressEntityJpa.from(family.getAddress()));
        }
        
        return entity;
    }
    
    public Family toModel() {
        Address modelAddress = null;
        if (this.getAddress() != null) {
            modelAddress =  this.getAddress().toModel();
        }
        
        return new Family(
            this.id,
            this.name,
            modelAddress,
            this.version,
            this.createdAt,
            this.updatedAt
        );
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressEntityJpa getAddress() {
        return address;
    }

    public void setAddress(AddressEntityJpa address) {
        this.address = address;
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
    
    public Integer getVersion() {
        return version;
    }


}
