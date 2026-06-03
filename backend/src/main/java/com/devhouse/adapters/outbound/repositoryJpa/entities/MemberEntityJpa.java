package com.devhouse.adapters.outbound.repositoryJpa.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "members")
public class MemberEntityJpa {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private FamilyEntityJpa family;

    @ManyToOne
    @JoinColumn(name = "cell_church_id")
    private CellChurchEntityJpa cellChurch;

    private Integer version;

    private String name;

    private String ministry;

    private String nickname;

    @Column(name = "profile_photo")
    private String profilePhoto;

    private String observation;

    @Column(name = "family_order")
    private Integer familyOrder;

    private Instant birthdate;

    @Column(name = "presentation_date")
    private Instant presentationDate;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean active;

    @Column(name = "is_baptized")
    private boolean isBaptized;

    private String genre;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public void setVersion(Integer version) {
        if (!Objects.equals(version, this.version)) {
            throw new OptimisticLockException("Error optimistic locking member", new Exception());
        }
    }
}
