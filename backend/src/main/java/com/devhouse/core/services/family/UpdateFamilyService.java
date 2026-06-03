package com.devhouse.core.services.family;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;
import com.devhouse.core.model.validator.FamilyModelValidator;
import com.devhouse.core.ports.inbound.family.UpdateFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;

import java.time.Instant;

public class UpdateFamilyService implements UpdateFamilyInboundPort {

    private final FamilyRepository familyRepository;
    private final FamilyModelValidator validator = new FamilyModelValidator();

    public UpdateFamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public FamilyResponse execute(Family family) {
        validator.validate(family);
        Family existing = familyRepository.findById(family.getId())
                .orElseThrow(() -> new DomainException("Family not found: " + family.getId()));
        Family toUpdate = new Family(
                family.getId(),
                family.getName(),
                family.getAddress(),
                family.getVersion(),
                existing.getCreatedDate(),
                Instant.now()
        );
        Family updated = familyRepository.update(toUpdate);
        return updated.toResponse();
    }
}
