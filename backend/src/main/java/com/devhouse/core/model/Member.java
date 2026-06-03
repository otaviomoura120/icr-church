package com.devhouse.core.model;

import com.devhouse.core.model.response.MemberResponse;

import java.time.Instant;

public class Member {
    private Long id;
    private Family family;
    private CellChurch cellChurch;
    private Integer version;
    private String name;
    private String ministry;
    private String nickname;
    private String profilePhoto;
    private String observation;
    private Integer familyOrder;
    private Instant birthdate;
    private Instant presentationDate;
    private String email;
    private String phoneNumber;
    private boolean active;
    private boolean isBaptized;
    private String genre;
    private String maritalStatus;
    private Instant createdDate;
    private Instant updatedDate;

    public Member(Long id, Family family, CellChurch cellChurch, Integer version, String name, String ministry, String nickname, String profilePhoto, String observation, Integer familyOrder, Instant birthdate, Instant presentationDate, String email, String phoneNumber, boolean active, boolean isBaptized, String genre, String maritalStatus, Instant createdDate, Instant updatedDate) {
        this.id = id;
        this.family = family;
        this.cellChurch = cellChurch;
        this.version = version;
        this.name = name;
        this.ministry = ministry;
        this.nickname = nickname;
        this.profilePhoto = profilePhoto;
        this.observation = observation;
        this.familyOrder = familyOrder;
        this.birthdate = birthdate;
        this.presentationDate = presentationDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.isBaptized = isBaptized;
        this.genre = genre;
        this.maritalStatus = maritalStatus;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public MemberResponse toResponse() {
        Long familyId = family != null ? family.getId() : null;
        Long cellChurchId = cellChurch != null ? cellChurch.getId() : null;
        return new MemberResponse(id, version, familyId, cellChurchId, name, ministry, nickname,
                profilePhoto, observation, familyOrder, birthdate, presentationDate, email, phoneNumber,
                active, isBaptized, genre, maritalStatus, createdDate, updatedDate);
    }

    public void updatePhotoPath(String path) {
        this.profilePhoto = path;
    }

    public Long getId() { return id; }
    public Family getFamily() { return family; }
    public CellChurch getCellChurch() { return cellChurch; }
    public Integer getVersion() { return version; }
    public String getName() { return name; }
    public String getMinistry() { return ministry; }
    public String getNickname() { return nickname; }
    public String getProfilePhoto() { return profilePhoto; }
    public String getObservation() { return observation; }
    public Integer getFamilyOrder() { return familyOrder; }
    public Instant getBirthdate() { return birthdate; }
    public Instant getPresentationDate() { return presentationDate; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public boolean isActive() { return active; }
    public boolean isBaptized() { return isBaptized; }
    public String getGenre() { return genre; }
    public String getMaritalStatus() { return maritalStatus; }
    public Instant getCreatedDate() { return createdDate; }
    public Instant getUpdatedDate() { return updatedDate; }
}
