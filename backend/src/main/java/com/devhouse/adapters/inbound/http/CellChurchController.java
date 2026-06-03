package com.devhouse.adapters.inbound.http;

import com.devhouse.adapters.inbound.http.converter.CellChurchConverter;
import com.devhouse.adapters.inbound.http.dto.request.CreateCellChurchRequestDto;
import com.devhouse.adapters.inbound.http.dto.request.UpdateCellChurchRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.CellChurchPageResponseDto;
import com.devhouse.adapters.inbound.http.dto.response.CellChurchResponseDto;
import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.model.response.CellChurchResponse;
import com.devhouse.core.ports.inbound.cellchurch.CreateCellChurchInboundPort;
import com.devhouse.core.ports.inbound.cellchurch.DeleteCellChurchInboundPort;
import com.devhouse.core.ports.inbound.cellchurch.SearchCellChurchInboundPort;
import com.devhouse.core.ports.inbound.cellchurch.UpdateCellChurchInboundPort;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@Controller("/cell-church")
@ExecuteOn(TaskExecutors.BLOCKING)
public class CellChurchController {

    private final CreateCellChurchInboundPort createCellChurchInboundPort;
    private final UpdateCellChurchInboundPort updateCellChurchInboundPort;
    private final DeleteCellChurchInboundPort deleteCellChurchInboundPort;
    private final SearchCellChurchInboundPort searchCellChurchInboundPort;
    private final CellChurchConverter cellChurchConverter;

    public CellChurchController(CreateCellChurchInboundPort createCellChurchInboundPort,
                                UpdateCellChurchInboundPort updateCellChurchInboundPort,
                                DeleteCellChurchInboundPort deleteCellChurchInboundPort,
                                SearchCellChurchInboundPort searchCellChurchInboundPort,
                                CellChurchConverter cellChurchConverter) {
        this.createCellChurchInboundPort = createCellChurchInboundPort;
        this.updateCellChurchInboundPort = updateCellChurchInboundPort;
        this.deleteCellChurchInboundPort = deleteCellChurchInboundPort;
        this.searchCellChurchInboundPort = searchCellChurchInboundPort;
        this.cellChurchConverter = cellChurchConverter;
    }

    @Post
    HttpResponse<CellChurchResponseDto> createCellChurch(@Body @Valid CreateCellChurchRequestDto request) {
        CellChurch cellChurch = cellChurchConverter.createModel(request);
        CellChurchResponse response = createCellChurchInboundPort.execute(cellChurch);
        return HttpResponse.created(cellChurchConverter.toResponse(response));
    }

    @Put("/{id}")
    HttpResponse<CellChurchResponseDto> updateCellChurch(@PathVariable Long id,
                                                         @Body @Valid UpdateCellChurchRequestDto request) {
        CellChurch cellChurch = cellChurchConverter.updateModel(id, request);
        CellChurchResponse response = updateCellChurchInboundPort.execute(cellChurch);
        return HttpResponse.ok(cellChurchConverter.toResponse(response));
    }

    @Delete("/{id}")
    HttpResponse<Void> deleteCellChurch(@PathVariable Long id) {
        deleteCellChurchInboundPort.execute(id);
        return HttpResponse.noContent();
    }

    @Get
    HttpResponse<CellChurchPageResponseDto> searchCellChurches(@QueryValue @Nullable String search,
                                                                @QueryValue @Nullable String sortBy,
                                                                @QueryValue @Nullable String sortDirection,
                                                                @QueryValue @Nullable Integer page,
                                                                @QueryValue @Nullable Integer size) {
        SearchQuery query = SearchQuery.withDefaults(search, page, size, sortBy, sortDirection);
        PagedResult<CellChurchResponse> result = searchCellChurchInboundPort.execute(query);
        return HttpResponse.ok(cellChurchConverter.toPageResponse(result));
    }
}
