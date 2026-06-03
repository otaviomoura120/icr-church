package com.devhouse.core.ports.inbound.family;

import com.devhouse.core.model.FamilySearchQuery;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.response.FamilyResponse;

public interface SearchFamilyInboundPort {
    PagedResult<FamilyResponse> execute(FamilySearchQuery query);
}
