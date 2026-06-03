package com.devhouse.config;

import com.devhouse.adapters.inbound.http.converter.FamilyConverter;
import com.devhouse.core.ports.inbound.family.CreateFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.DeleteFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.SearchFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.UpdateFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.core.services.family.CreateFamilyService;
import com.devhouse.core.services.family.DeleteFamilyService;
import com.devhouse.core.services.family.SearchFamilyService;
import com.devhouse.core.services.family.UpdateFamilyService;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
public class BeanFactory {

    @Bean
    public CreateFamilyInboundPort createFamilyInboundPort(FamilyRepository familyRepository) {
        return new CreateFamilyService(familyRepository);
    }

    @Bean
    public UpdateFamilyInboundPort updateFamilyInboundPort(FamilyRepository familyRepository) {
        return new UpdateFamilyService(familyRepository);
    }

    @Bean
    public DeleteFamilyInboundPort deleteFamilyInboundPort(FamilyRepository familyRepository) {
        return new DeleteFamilyService(familyRepository);
    }

    @Bean
    public SearchFamilyInboundPort searchFamilyInboundPort(FamilyRepository familyRepository) {
        return new SearchFamilyService(familyRepository);
    }

    @Bean
    public FamilyConverter familyConverter() {
        return new FamilyConverter();
    }

    @Bean
    public CreateFamilyService createFamilyService(FamilyRepository familyRepository) {
        return new CreateFamilyService(familyRepository);
    }
}
