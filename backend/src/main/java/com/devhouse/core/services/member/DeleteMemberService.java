package com.devhouse.core.services.member;

import com.devhouse.core.ports.inbound.member.DeleteMemberInboundPort;
import com.devhouse.core.ports.outbound.FileStoragePort;
import com.devhouse.core.ports.outbound.MemberRepository;

public class DeleteMemberService implements DeleteMemberInboundPort {

    private final MemberRepository memberRepository;
    private final FileStoragePort fileStoragePort;

    public DeleteMemberService(MemberRepository memberRepository, FileStoragePort fileStoragePort) {
        this.memberRepository = memberRepository;
        this.fileStoragePort = fileStoragePort;
    }

    @Override
    public void execute(Long id) {
        memberRepository.findById(id).ifPresent(member -> {
            if (member.getProfilePhoto() != null) {
                fileStoragePort.delete(member.getProfilePhoto());
            }
        });
        memberRepository.delete(id);
    }
}
