package com.devhouse.core.ports.inbound.member;

import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.MemberResponse;

public interface SearchMemberInboundPort {
    PagedResult<MemberResponse> execute(SearchQuery query);
}
