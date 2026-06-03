package com.devhouse.adapters.inbound.http.converter;

import com.devhouse.adapters.inbound.http.dto.request.CreateFamilyRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.CreateFamilyResponseDto;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;

public class FamilyConverter {

    public Family createModel(CreateFamilyRequestDto requestDto) {
        return new Family(requestDto.name(), requestDto.address().createModel());
    }

    public CreateFamilyResponseDto toResponse(FamilyResponse familyResponse) {
        return new CreateFamilyResponseDto(familyResponse.id(), familyResponse.name());
    }
}
