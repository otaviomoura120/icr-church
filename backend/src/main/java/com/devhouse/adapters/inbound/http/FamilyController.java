package com.devhouse.adapters.inbound.http;

import com.devhouse.adapters.inbound.http.converter.FamilyConverter;
import com.devhouse.adapters.inbound.http.dto.request.CreateFamilyRequestDto;
import com.devhouse.adapters.inbound.http.dto.request.UpdateFamilyRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.CreateFamilyResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.FamilyPageResponseDto;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.response.FamilyResponse;
import com.devhouse.core.ports.inbound.family.CreateFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.DeleteFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.SearchFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.UpdateFamilyInboundPort;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@Controller("/family")
@ExecuteOn(TaskExecutors.BLOCKING)
public class FamilyController {

    private final CreateFamilyInboundPort createFamilyInboundPort;
    private final UpdateFamilyInboundPort updateFamilyInboundPort;
    private final DeleteFamilyInboundPort deleteFamilyInboundPort;
    private final SearchFamilyInboundPort searchFamilyInboundPort;
    private final FamilyConverter familyConverter;

    public FamilyController(CreateFamilyInboundPort createFamilyInboundPort,
                            UpdateFamilyInboundPort updateFamilyInboundPort,
                            DeleteFamilyInboundPort deleteFamilyInboundPort,
                            SearchFamilyInboundPort searchFamilyInboundPort,
                            FamilyConverter familyConverter) {
        this.createFamilyInboundPort = createFamilyInboundPort;
        this.updateFamilyInboundPort = updateFamilyInboundPort;
        this.deleteFamilyInboundPort = deleteFamilyInboundPort;
        this.searchFamilyInboundPort = searchFamilyInboundPort;
        this.familyConverter = familyConverter;
    }

    @Post
    HttpResponse<CreateFamilyResponseDto> createFamily(@Body @Valid CreateFamilyRequestDto createFamilyRequest) {
        Family family = familyConverter.createModel(createFamilyRequest);
        FamilyResponse familyResponse = createFamilyInboundPort.execute(family);
        CreateFamilyResponseDto responseDto = familyConverter.toResponse(familyResponse);
        return HttpResponse.created(responseDto);
    }

    @Put("/{id}")
    HttpResponse<CreateFamilyResponseDto> updateFamily(@PathVariable Long id,
                                                       @Body @Valid UpdateFamilyRequestDto updateFamilyRequest) {
        Family family = familyConverter.updateModel(id, updateFamilyRequest);
        FamilyResponse familyResponse = updateFamilyInboundPort.execute(family);
        CreateFamilyResponseDto responseDto = familyConverter.toResponse(familyResponse);
        return HttpResponse.ok(responseDto);
    }

    @Delete("/{id}")
    HttpResponse<Void> deleteFamily(@PathVariable Long id) {
        deleteFamilyInboundPort.execute(id);
        return HttpResponse.noContent();
    }

    @Get
    HttpResponse<FamilyPageResponseDto> searchFamilies(@QueryValue @Nullable String search,
                                                       @QueryValue @Nullable String sortBy,
                                                       @QueryValue @Nullable String sortDirection,
                                                       @QueryValue @Nullable Integer page,
                                                       @QueryValue @Nullable Integer size) {
        SearchQuery query = SearchQuery.withDefaults(search, page, size, sortBy, sortDirection);
        PagedResult<FamilyResponse> result = searchFamilyInboundPort.execute(query);
        FamilyPageResponseDto responseDto = familyConverter.toPageResponse(result);
        return HttpResponse.ok(responseDto);
    }
}
