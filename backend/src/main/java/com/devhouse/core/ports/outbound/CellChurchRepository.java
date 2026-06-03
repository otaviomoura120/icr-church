package com.devhouse.core.ports.outbound;

import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;

import java.util.Optional;

public interface CellChurchRepository {
    CellChurch save(CellChurch cellChurch);
    CellChurch update(CellChurch cellChurch);
    Optional<CellChurch> findById(Long id);
    void delete(Long id);
    PagedResult<CellChurch> findAll(SearchQuery query);
}
