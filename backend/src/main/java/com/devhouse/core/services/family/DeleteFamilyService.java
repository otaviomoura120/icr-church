package com.devhouse.core.services.family;

import com.devhouse.core.ports.inbound.family.DeleteFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;

public class DeleteFamilyService implements DeleteFamilyInboundPort {

    private final FamilyRepository familyRepository;

    public DeleteFamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public void execute(Long id) {
        familyRepository.delete(id);
    }
}
