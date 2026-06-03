package com.devhouse.adapters.inbound.http.dto.response;

import java.util.List;

public record MemberPageResponseDto(
        List<MemberResponseDto> content,
        Long totalElements,
        int totalPages,
        int page,
        int size
) {}
