package com.devhouse.adapters.inbound.http.converter;

import com.devhouse.adapters.inbound.http.dto.request.CreateCellChurchRequestDto;
import com.devhouse.adapters.inbound.http.dto.request.UpdateCellChurchRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.AddressResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.CellChurchPageResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.CellChurchResponseDto;
import com.devhouse.core.model.Address;
import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.CellProfile;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.response.CellChurchResponse;

import java.time.Instant;
import java.time.OffsetTime;
import java.util.List;

public class CellChurchConverter {

    public CellChurch createModel(CreateCellChurchRequestDto dto) {
        CellProfile cellProfile = new CellProfile(dto.cellProfileId(), null, null, null, null);
        Address address = dto.address() != null ? dto.address().createModel() : null;
        OffsetTime hour = dto.hour() != null ? OffsetTime.parse(dto.hour()) : null;
        return new CellChurch(dto.name(), cellProfile, address, dto.weekday(), hour);
    }

    public CellChurch updateModel(Long id, UpdateCellChurchRequestDto dto) {
        CellProfile cellProfile = new CellProfile(dto.cellProfileId(), null, null, null, null);
        Address address = dto.address() != null ? dto.address().createModel() : null;
        OffsetTime hour = dto.hour() != null ? OffsetTime.parse(dto.hour()) : null;
        return new CellChurch(id, dto.version(), cellProfile, address, dto.name(), dto.weekday(), hour, Instant.now(), Instant.now());
    }

    public CellChurchResponseDto toResponse(CellChurchResponse response) {
        Long cellProfileId = response.cellProfile() != null ? response.cellProfile().getId() : null;
        String cellProfileName = response.cellProfile() != null ? response.cellProfile().getName() : null;
        return new CellChurchResponseDto(
                response.id(),
                response.name(),
                response.version(),
                cellProfileId,
                cellProfileName,
                toAddressResponse(response.address()),
                response.weekday(),
                response.hour(),
                response.createdAt(),
                response.updatedAt()
        );
    }

    public CellChurchPageResponseDto toPageResponse(PagedResult<CellChurchResponse> page) {
        List<CellChurchResponseDto> items = page.content().stream()
                .map(this::toResponse)
                .toList();
        return new CellChurchPageResponseDto(items, page.totalElements(), page.totalPages(), page.page(), page.size());
    }

    private AddressResponseDto toAddressResponse(Address address) {
        if (address == null) return null;
        return new AddressResponseDto(
                address.getStreet(),
                address.getZipCode(),
                address.getCountry(),
                address.getState(),
                address.getCity(),
                address.getNeighborhood()
        );
    }
}
