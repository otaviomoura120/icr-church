package com.devhouse.core.services.member;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Member;
import com.devhouse.core.model.response.MemberResponse;
import com.devhouse.core.model.validator.MemberModelValidator;
import com.devhouse.core.ports.inbound.member.UpdateMemberInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.core.ports.outbound.MemberRepository;

import java.time.Instant;

public class UpdateMemberService implements UpdateMemberInboundPort {

    private final MemberRepository memberRepository;
    private final FamilyRepository familyRepository;
    private final CellChurchRepository cellChurchRepository;
    private final MemberModelValidator validator = new MemberModelValidator();

    public UpdateMemberService(MemberRepository memberRepository,
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
        Member existing = memberRepository.findById(member.getId())
                .orElseThrow(() -> new DomainException("Member not found with id: " + member.getId()));
        Member toUpdate = new Member(
                member.getId(), member.getFamily(), member.getCellChurch(), member.getVersion(),
                member.getName(), member.getMinistry(), member.getNickname(), member.getProfilePhoto(),
                member.getObservation(), member.getFamilyOrder(), member.getBirthdate(),
                member.getPresentationDate(), member.getEmail(), member.getPhoneNumber(),
                member.isActive(), member.isBaptized(), member.getGenre(), member.getMaritalStatus(),
                existing.getCreatedDate(), Instant.now()
        );
        return memberRepository.update(toUpdate).toResponse();
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
