package com.devhouse.adapters.outbound.repositoryJpa.entities;

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

}
