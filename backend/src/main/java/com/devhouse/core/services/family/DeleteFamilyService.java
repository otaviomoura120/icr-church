package com.devhouse.core.services.family;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.ports.inbound.family.DeleteFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.core.ports.outbound.MemberRepository;

public class DeleteFamilyService implements DeleteFamilyInboundPort {

    private final FamilyRepository familyRepository;
    private final MemberRepository memberRepository;

    public DeleteFamilyService(FamilyRepository familyRepository, MemberRepository memberRepository) {
        this.familyRepository = familyRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void execute(Long id) {
        if (memberRepository.countByFamilyId(id) > 0) {
            throw new DomainException("Não é possível excluir a família pois existem membros vinculados a ela.");
        }
        familyRepository.delete(id);
    }
}
