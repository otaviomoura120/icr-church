package com.devhouse.core.services;

import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;
import com.devhouse.core.ports.inbound.CreateFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;

public class CreateFamilyService implements CreateFamilyInboundPort {

    private final FamilyRepository familyRepository;

    public CreateFamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public FamilyResponse execute(Family family) {
        family = familyRepository.save(family);
        return family.toResponse();

    }
}
