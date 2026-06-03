package com.devhouse.core.ports.inbound.cellchurch;

import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.CellChurchResponse;

public interface SearchCellChurchInboundPort {
    PagedResult<CellChurchResponse> execute(SearchQuery query);
}
