package com.devhouse.core.services.family;

import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;
import com.devhouse.core.model.validator.FamilyModelValidator;
import com.devhouse.core.ports.inbound.family.CreateFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;

public class CreateFamilyService implements CreateFamilyInboundPort {

    private final FamilyRepository familyRepository;
    private final FamilyModelValidator validator = new FamilyModelValidator();

    public CreateFamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public FamilyResponse execute(Family family) {
        validator.validate(family);
        family = familyRepository.save(family);
        return family.toResponse();
    }
}
