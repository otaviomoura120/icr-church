package com.devhouse.core.ports.inbound.member;

import com.devhouse.core.model.Member;
import com.devhouse.core.model.response.MemberResponse;

public interface UpdateMemberInboundPort {
    MemberResponse execute(Member member);
}
