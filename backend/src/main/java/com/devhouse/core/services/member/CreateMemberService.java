package com.devhouse.core.services.member;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Member;
import com.devhouse.core.model.response.MemberResponse;
import com.devhouse.core.model.validator.MemberModelValidator;
import com.devhouse.core.ports.inbound.member.CreateMemberInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.core.ports.outbound.MemberRepository;

public class CreateMemberService implements CreateMemberInboundPort {

    private final MemberRepository memberRepository;
    private final FamilyRepository familyRepository;
    private final CellChurchRepository cellChurchRepository;
    private final MemberModelValidator validator = new MemberModelValidator();

    public CreateMemberService(MemberRepository memberRepository,
                               FamilyRepository familyRepository,
                               CellChurchRepository cellChurchRepository) {
        this.memberRepository = memberRepository;
        this.familyRepository = familyRepository;
        this.cellChurchRepository = cellChurchRepository;
    }

    @Override
    public MemberResponse execute(Member member) {
        validator.validate(member);
        validateRelationships(member);
        Member saved = memberRepository.save(member);
        return saved.toResponse();
    }

    private void validateRelationships(Member member) {
        if (member.getFamily() != null && member.getFamily().getId() != null) {
            familyRepository.findById(member.getFamily().getId())
                    .orElseThrow(() -> new DomainException("Family not found with id: " + member.getFamily().getId()));
        }
        if (member.getCellChurch() != null && member.getCellChurch().getId() != null) {
            cellChurchRepository.findById(member.getCellChurch().getId())
                    .orElseThrow(() -> new DomainException("Cell church not found with id: " + member.getCellChurch().getId()));
        }
    }
}
