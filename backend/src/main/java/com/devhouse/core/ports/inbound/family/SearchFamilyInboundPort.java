package com.devhouse.core.ports.inbound.family;

import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.FamilyResponse;

public interface SearchFamilyInboundPort {
    PagedResult<FamilyResponse> execute(SearchQuery query);
}
