package com.devhouse.config;

import com.devhouse.adapters.inbound.http.converter.FamilyConverter;
import com.devhouse.core.ports.inbound.CreateFamilyInboundPort;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.core.services.CreateFamilyService;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
public class BeanFactory {

    @Bean
    public CreateFamilyInboundPort createFamilyInboundPort(FamilyRepository familyRepository) {
        return new CreateFamilyService(familyRepository);
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
