package com.devhouse.core.services.family;

import com.devhouse.core.model.Family;
import com.devhouse.core.model.FamilySearchQuery;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.response.FamilyResponse;
import com.devhouse.core.ports.inbound.family.SearchFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;

import java.util.List;

public class SearchFamilyService implements SearchFamilyInboundPort {

    private final FamilyRepository familyRepository;

    public SearchFamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public PagedResult<FamilyResponse> execute(FamilySearchQuery query) {
        PagedResult<Family> result = familyRepository.findAll(query);
        List<FamilyResponse> responses = result.content().stream()
                .map(Family::toResponse)
                .toList();
        return new PagedResult<>(responses, result.totalElements(), result.totalPages(), result.page(), result.size());
    }
}
