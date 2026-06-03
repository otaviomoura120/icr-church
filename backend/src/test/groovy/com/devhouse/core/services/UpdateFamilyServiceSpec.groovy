package com.devhouse.core.services

import com.devhouse.config.exception.DomainException
import com.devhouse.core.model.Address
import com.devhouse.core.model.Family
import com.devhouse.core.model.response.FamilyResponse
import com.devhouse.core.ports.outbound.FamilyRepository
import com.devhouse.core.services.family.UpdateFamilyService
import spock.lang.Specification

import java.time.Instant

class UpdateFamilyServiceSpec extends Specification {

    FamilyRepository familyRepository = Mock()
    UpdateFamilyService service = new UpdateFamilyService(familyRepository)

    void "should update family and return response"() {
        given:
        Instant createdAt = Instant.parse("2024-01-01T00:00:00Z")
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        Family family = new Family(1L, "Silva Updated", address, 0, Instant.now(), Instant.now())
        Family existing = new Family(1L, "Silva", address, 0, createdAt, createdAt)
        Family updated = new Family(1L, "Silva Updated", address, 1, createdAt, Instant.now())
        familyRepository.findById(1L) >> Optional.of(existing)
        familyRepository.update(_ as Family) >> updated

        when:
        FamilyResponse result = service.execute(family)

        then:
        result.id() == 1L
        result.name() == "Silva Updated"
    }

    void "should throw exception when name is blank"() {
        given:
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        Family family = new Family(1L, "", address, 0, Instant.now(), Instant.now())

        when:
        service.execute(family)

        then:
        thrown(DomainException)
    }

    void "should throw exception when family not found"() {
        given:
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        Family family = new Family(99L, "Silva", address, 0, Instant.now(), Instant.now())
        familyRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(family)

        then:
        thrown(DomainException)
    }
}
