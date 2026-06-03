package com.devhouse.adapters.inbound.http.converter;

import com.devhouse.adapters.inbound.http.dto.request.CreateMemberRequestDto;
import com.devhouse.adapters.inbound.http.dto.request.UpdateMemberRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.MemberPageResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.MemberResponseDto;
import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.Member;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.response.MemberResponse;

import java.time.Instant;
import java.util.List;

public class MemberConverter {

    public Member createModel(CreateMemberRequestDto dto) {
        Family family = dto.familyId() != null ? new Family(dto.familyId(), null, null, null, null, null) : null;
        CellChurch cellChurch = dto.cellChurchId() != null ? CellChurch.stub(dto.cellChurchId()) : null;
        return new Member(null, family, cellChurch, null, dto.name(), dto.ministry(), dto.nickname(),
                null, dto.observation(), dto.familyOrder(), dto.birthdate(), dto.presentationDate(),
                dto.email(), dto.phoneNumber(), dto.active(), dto.isBaptized(), dto.genre(),
                dto.maritalStatus(), Instant.now(), Instant.now());
    }

    public Member updateModel(Long id, UpdateMemberRequestDto dto) {
        Family family = dto.familyId() != null ? new Family(dto.familyId(), null, null, null, null, null) : null;
        CellChurch cellChurch = dto.cellChurchId() != null ? CellChurch.stub(dto.cellChurchId()) : null;
        return new Member(id, family, cellChurch, dto.version(), dto.name(), dto.ministry(), dto.nickname(),
                null, dto.observation(), dto.familyOrder(), dto.birthdate(), dto.presentationDate(),
                dto.email(), dto.phoneNumber(), dto.active(), dto.isBaptized(), dto.genre(),
                dto.maritalStatus(), null, null);
    }

    public MemberResponseDto toResponse(MemberResponse response) {
        return new MemberResponseDto(
                response.id(), response.version(), response.familyId(), response.cellChurchId(),
                response.name(), response.ministry(), response.nickname(), response.profilePhoto(),
                response.observation(), response.familyOrder(), response.birthdate(),
                response.presentationDate(), response.email(), response.phoneNumber(),
                response.active(), response.isBaptized(), response.genre(), response.maritalStatus(),
                response.createdDate(), response.updatedDate()
        );
    }

    public MemberPageResponseDto toPageResponse(PagedResult<MemberResponse> page) {
        List<MemberResponseDto> items = page.content().stream()
                .map(this::toResponse)
                .toList();
        return new MemberPageResponseDto(items, page.totalElements(), page.totalPages(), page.page(), page.size());
    }
}
