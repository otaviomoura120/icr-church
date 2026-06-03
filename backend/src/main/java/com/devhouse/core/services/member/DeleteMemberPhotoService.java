package com.devhouse.core.services.member;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Member;
import com.devhouse.core.ports.inbound.member.DeleteMemberPhotoInboundPort;
import com.devhouse.core.ports.outbound.FileStoragePort;
import com.devhouse.core.ports.outbound.MemberRepository;

public class DeleteMemberPhotoService implements DeleteMemberPhotoInboundPort {

    private final MemberRepository memberRepository;
    private final FileStoragePort fileStoragePort;

    public DeleteMemberPhotoService(MemberRepository memberRepository, FileStoragePort fileStoragePort) {
        this.memberRepository = memberRepository;
        this.fileStoragePort = fileStoragePort;
    }

    @Override
    public void execute(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new DomainException("Member not found with id: " + id));

        if (member.getProfilePhoto() == null) {
            return;
        }

        fileStoragePort.delete(member.getProfilePhoto());

        member.updatePhotoPath(null);
        memberRepository.update(member);
    }
}
