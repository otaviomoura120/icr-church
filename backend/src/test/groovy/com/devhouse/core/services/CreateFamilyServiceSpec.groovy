package com.devhouse.core.services

import com.devhouse.config.exception.DomainException
import com.devhouse.core.model.Address
import com.devhouse.core.model.Family
import com.devhouse.core.model.response.FamilyResponse
import com.devhouse.core.ports.outbound.FamilyRepository
import com.devhouse.core.services.family.CreateFamilyService
import spock.lang.Specification

import java.time.Instant

class CreateFamilyServiceSpec extends Specification {

    FamilyRepository familyRepository = Mock()
    CreateFamilyService service = new CreateFamilyService(familyRepository)

    void "should save family and return response"() {
        given:
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        Family family = new Family("Silva", address)
        Family saved = new Family(1L, "Silva", address, 0, Instant.now(), Instant.now())
        familyRepository.save(family) >> saved

        when:
        FamilyResponse result = service.execute(family)

        then:
        result.id() == 1L
        result.name() == "Silva"
    }

    void "should throw exception when name is blank"() {
        given:
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        Family family = new Family("", address)

        when:
        service.execute(family)

        then:
        thrown(DomainException)
    }

    void "should throw exception when name is null"() {
        given:
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        Family family = new Family(null, address)

        when:
        service.execute(family)

        then:
        thrown(DomainException)
    }
}
