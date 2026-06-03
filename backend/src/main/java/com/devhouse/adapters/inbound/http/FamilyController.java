package com.devhouse.adapters.inbound.http;

import com.devhouse.adapters.inbound.http.converter.FamilyConverter;
import com.devhouse.adapters.inbound.http.dto.request.CreateFamilyRequestDto;
import com.devhouse.adapters.inbound.http.dto.response.CreateFamilyResponseDto;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;
import com.devhouse.core.ports.inbound.CreateFamilyInboundPort;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;

@Controller("/family")
@ExecuteOn(TaskExecutors.BLOCKING)
public class FamilyController {

    private final CreateFamilyInboundPort createFamilyInboundPort;
    private final FamilyConverter familyConverter;

    public FamilyController(CreateFamilyInboundPort createFamilyInboundPort,
                            FamilyConverter familyConverter) {
        this.createFamilyInboundPort = createFamilyInboundPort;
        this.familyConverter = familyConverter;
    }

    @Post
    HttpResponse<CreateFamilyResponseDto> createSaasSubscription(@Body @Valid CreateFamilyRequestDto createFamilyRequest) {
        Family family = familyConverter.createModel(createFamilyRequest);
        FamilyResponse familyResponse = createFamilyInboundPort.execute(family);
        CreateFamilyResponseDto responseDto = familyConverter.toResponse(familyResponse);
        return HttpResponse.created(responseDto);
    }

}
