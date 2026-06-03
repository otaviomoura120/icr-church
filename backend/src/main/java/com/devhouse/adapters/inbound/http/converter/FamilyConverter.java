package com.devhouse.adapters.inbound.http.converter;

import com.devhouse.adapters.inbound.http.dto.request.CreateFamilyRequestDto;
import com.devhouse.adapters.inbound.http.dto.request.UpdateFamilyRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.CreateFamilyResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.FamilyPageResponseDto;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.response.FamilyResponse;

import java.time.Instant;
import java.util.List;

public class FamilyConverter {

    public Family createModel(CreateFamilyRequestDto requestDto) {
        return new Family(requestDto.name(), requestDto.address().createModel());
    }

    public Family updateModel(Long id, UpdateFamilyRequestDto requestDto) {
        return new Family(id, requestDto.name(), requestDto.address().createModel(), requestDto.version(), Instant.now(), Instant.now());
    }

    public CreateFamilyResponseDto toResponse(FamilyResponse familyResponse) {
        return new CreateFamilyResponseDto(familyResponse.id(), familyResponse.name());
    }

    public FamilyPageResponseDto toPageResponse(PagedResult<FamilyResponse> page) {
        List<CreateFamilyResponseDto> items = page.content().stream()
                .map(r -> new CreateFamilyResponseDto(r.id(), r.name()))
                .toList();
        return new FamilyPageResponseDto(items, page.totalElements(), page.totalPages(), page.page(), page.size());
    }
}
