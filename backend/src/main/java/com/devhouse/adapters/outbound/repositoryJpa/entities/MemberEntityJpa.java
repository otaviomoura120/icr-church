package com.devhouse.adapters.outbound.repositoryJpa.entities;

import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.Member;
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

    @Version
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

    public static MemberEntityJpa from(Member member) {
        if (member == null) return null;
        MemberEntityJpa entity = new MemberEntityJpa();
        entity.setId(member.getId());
        entity.version = member.getVersion();
        entity.setName(member.getName());
        entity.setMinistry(member.getMinistry());
        entity.setNickname(member.getNickname());
        entity.setProfilePhoto(member.getProfilePhoto());
        entity.setObservation(member.getObservation());
        entity.setFamilyOrder(member.getFamilyOrder());
        entity.setBirthdate(member.getBirthdate());
        entity.setPresentationDate(member.getPresentationDate());
        entity.setEmail(member.getEmail());
        entity.setPhoneNumber(member.getPhoneNumber());
        entity.setActive(member.isActive());
        entity.setBaptized(member.isBaptized());
        entity.setGenre(member.getGenre());
        entity.setMaritalStatus(member.getMaritalStatus());
        entity.setCreatedAt(member.getCreatedDate());
        entity.setUpdatedAt(member.getUpdatedDate());
        if (member.getFamily() != null) {
            entity.setFamily(FamilyEntityJpa.stub(member.getFamily().getId()));
        }
        if (member.getCellChurch() != null) {
            entity.setCellChurch(CellChurchEntityJpa.stub(member.getCellChurch().getId()));
        }
        return entity;
    }

    public Member toModel() {
        Family familyModel = family != null ? family.toModel() : null;
        CellChurch cellChurchModel = cellChurch != null ? cellChurch.toModel() : null;
        return new Member(id, familyModel, cellChurchModel, version, name, ministry, nickname,
                profilePhoto, observation, familyOrder, birthdate, presentationDate, email, phoneNumber,
                active, isBaptized, genre, maritalStatus, createdAt, updatedAt);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public FamilyEntityJpa getFamily() { return family; }
    public void setFamily(FamilyEntityJpa family) { this.family = family; }
    public CellChurchEntityJpa getCellChurch() { return cellChurch; }
    public void setCellChurch(CellChurchEntityJpa cellChurch) { this.cellChurch = cellChurch; }
    public Integer getVersion() { return version; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMinistry() { return ministry; }
    public void setMinistry(String ministry) { this.ministry = ministry; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
    public Integer getFamilyOrder() { return familyOrder; }
    public void setFamilyOrder(Integer familyOrder) { this.familyOrder = familyOrder; }
    public Instant getBirthdate() { return birthdate; }
    public void setBirthdate(Instant birthdate) { this.birthdate = birthdate; }
    public Instant getPresentationDate() { return presentationDate; }
    public void setPresentationDate(Instant presentationDate) { this.presentationDate = presentationDate; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public boolean isBaptized() { return isBaptized; }
    public void setBaptized(boolean baptized) { isBaptized = baptized; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
