package com.devhouse.core.services.member;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Member;
import com.devhouse.core.model.response.MemberResponse;
import com.devhouse.core.ports.inbound.member.UploadMemberPhotoInboundPort;
import com.devhouse.core.ports.outbound.FileStoragePort;
import com.devhouse.core.ports.outbound.MemberRepository;

public class UploadMemberPhotoService implements UploadMemberPhotoInboundPort {

    private final MemberRepository memberRepository;
    private final FileStoragePort fileStoragePort;

    public UploadMemberPhotoService(MemberRepository memberRepository, FileStoragePort fileStoragePort) {
        this.memberRepository = memberRepository;
        this.fileStoragePort = fileStoragePort;
    }

    @Override
    public MemberResponse execute(Long id, byte[] data, String filename) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new DomainException("Member not found with id: " + id));

        if (member.getProfilePhoto() != null) {
            fileStoragePort.delete(member.getProfilePhoto());
        }

        String relativePath = "files/profile-photos/" + id + "/" + filename;
        fileStoragePort.save(relativePath, data);

        member.updatePhotoPath(relativePath);

        return memberRepository.update(member).toResponse();
    }
}
