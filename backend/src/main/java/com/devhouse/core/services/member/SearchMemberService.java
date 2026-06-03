package com.devhouse.core.services.member;

import com.devhouse.core.model.Member;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.MemberResponse;
import com.devhouse.core.ports.inbound.member.SearchMemberInboundPort;
import com.devhouse.core.ports.outbound.MemberRepository;

import java.util.List;

public class SearchMemberService implements SearchMemberInboundPort {

    private final MemberRepository memberRepository;

    public SearchMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public PagedResult<MemberResponse> execute(SearchQuery query) {
        PagedResult<Member> result = memberRepository.findAll(query);
        List<MemberResponse> responses = result.content().stream()
                .map(Member::toResponse)
                .toList();
        return new PagedResult<>(responses, result.totalElements(), result.totalPages(), result.page(), result.size());
    }
}
