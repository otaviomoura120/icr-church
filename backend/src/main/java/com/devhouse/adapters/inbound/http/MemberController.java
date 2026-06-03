package com.devhouse.adapters.inbound.http;

import com.devhouse.adapters.inbound.http.converter.MemberConverter;
import com.devhouse.adapters.inbound.http.dto.request.CreateMemberRequestDto;
import com.devhouse.adapters.inbound.http.dto.request.UpdateMemberRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.MemberPageResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.MemberResponseDto;
import com.devhouse.core.model.Member;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.MemberResponse;
import com.devhouse.core.ports.inbound.member.CreateMemberInboundPort;
import com.devhouse.core.ports.inbound.member.DeleteMemberInboundPort;
import com.devhouse.core.ports.inbound.member.DeleteMemberPhotoInboundPort;
import com.devhouse.core.ports.inbound.member.SearchMemberInboundPort;
import com.devhouse.core.ports.inbound.member.UpdateMemberInboundPort;
import com.devhouse.core.ports.inbound.member.UploadMemberPhotoInboundPort;
import io.micronaut.json.JsonMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.Nullable;

import java.io.IOException;

@Controller("/member")
@ExecuteOn(TaskExecutors.BLOCKING)
public class MemberController {

    private final CreateMemberInboundPort createMemberInboundPort;
    private final UpdateMemberInboundPort updateMemberInboundPort;
    private final DeleteMemberInboundPort deleteMemberInboundPort;
    private final SearchMemberInboundPort searchMemberInboundPort;
    private final UploadMemberPhotoInboundPort uploadMemberPhotoInboundPort;
    private final DeleteMemberPhotoInboundPort deleteMemberPhotoInboundPort;
    private final MemberConverter memberConverter;
    private final JsonMapper jsonMapper;

    public MemberController(CreateMemberInboundPort createMemberInboundPort,
                            UpdateMemberInboundPort updateMemberInboundPort,
                            DeleteMemberInboundPort deleteMemberInboundPort,
                            SearchMemberInboundPort searchMemberInboundPort,
                            UploadMemberPhotoInboundPort uploadMemberPhotoInboundPort,
                            DeleteMemberPhotoInboundPort deleteMemberPhotoInboundPort,
                            MemberConverter memberConverter,
                            JsonMapper jsonMapper) {
        this.createMemberInboundPort = createMemberInboundPort;
        this.updateMemberInboundPort = updateMemberInboundPort;
        this.deleteMemberInboundPort = deleteMemberInboundPort;
        this.searchMemberInboundPort = searchMemberInboundPort;
        this.uploadMemberPhotoInboundPort = uploadMemberPhotoInboundPort;
        this.deleteMemberPhotoInboundPort = deleteMemberPhotoInboundPort;
        this.memberConverter = memberConverter;
        this.jsonMapper = jsonMapper;
    }

    @Post
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    HttpResponse<MemberResponseDto> createMember(@Part("data") String rawData,
                                                  @Part("photo") @Nullable CompletedFileUpload photo) throws IOException {
        CreateMemberRequestDto data = jsonMapper.readValue(rawData, CreateMemberRequestDto.class);
        Member member = memberConverter.createModel(data);
        MemberResponse response = createMemberInboundPort.execute(member);
        if (photo != null && photo.getFilename() != null && !photo.getFilename().isBlank()) {
            response = uploadMemberPhotoInboundPort.execute(response.id(), photo.getBytes(), photo.getFilename());
        }
        return HttpResponse.created(memberConverter.toResponse(response));
    }

    @Put("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    HttpResponse<MemberResponseDto> updateMember(@PathVariable Long id,
                                                  @Part("data") String rawData,
                                                  @Part("photo") @Nullable CompletedFileUpload photo) throws IOException {
        UpdateMemberRequestDto data = jsonMapper.readValue(rawData, UpdateMemberRequestDto.class);
        Member member = memberConverter.updateModel(id, data);
        MemberResponse response = updateMemberInboundPort.execute(member);
        if (photo != null && photo.getFilename() != null && !photo.getFilename().isBlank()) {
            response = uploadMemberPhotoInboundPort.execute(response.id(), photo.getBytes(), photo.getFilename());
        }
        return HttpResponse.ok(memberConverter.toResponse(response));
    }

    @Delete("/{id}")
    HttpResponse<Void> deleteMember(@PathVariable Long id) {
        deleteMemberInboundPort.execute(id);
        return HttpResponse.noContent();
    }

    @Get
    HttpResponse<MemberPageResponseDto> searchMembers(@QueryValue @Nullable String search,
                                                       @QueryValue @Nullable String sortBy,
                                                       @QueryValue @Nullable String sortDirection,
                                                       @QueryValue @Nullable Integer page,
                                                       @QueryValue @Nullable Integer size) {
        SearchQuery query = SearchQuery.withDefaults(search, page, size, sortBy, sortDirection);
        PagedResult<MemberResponse> result = searchMemberInboundPort.execute(query);
        MemberPageResponseDto responseDto = memberConverter.toPageResponse(result);
        return HttpResponse.ok(responseDto);
    }

    @Delete("/{id}/photo")
    HttpResponse<Void> deleteMemberPhoto(@PathVariable Long id) {
        deleteMemberPhotoInboundPort.execute(id);
        return HttpResponse.noContent();
    }
}
