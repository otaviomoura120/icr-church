package com.devhouse.core.services.cellchurch;

import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.CellChurchResponse;
import com.devhouse.core.ports.inbound.cellchurch.SearchCellChurchInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;

import java.util.List;

public class SearchCellChurchService implements SearchCellChurchInboundPort {

    private final CellChurchRepository cellChurchRepository;

    public SearchCellChurchService(CellChurchRepository cellChurchRepository) {
        this.cellChurchRepository = cellChurchRepository;
    }

    @Override
    public PagedResult<CellChurchResponse> execute(SearchQuery query) {
        PagedResult<CellChurch> result = cellChurchRepository.findAll(query);
        List<CellChurchResponse> responses = result.content().stream()
                .map(CellChurch::toResponse)
                .toList();
        return new PagedResult<>(responses, result.totalElements(), result.totalPages(), result.page(), result.size());
    }
}
