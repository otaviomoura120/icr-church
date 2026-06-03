package com.devhouse.core.ports.inbound.member;

import com.devhouse.core.model.response.MemberResponse;

public interface UploadMemberPhotoInboundPort {
    MemberResponse execute(Long id, byte[] data, String filename);
}
