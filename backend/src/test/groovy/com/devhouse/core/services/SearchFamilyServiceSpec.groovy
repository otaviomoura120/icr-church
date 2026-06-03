package com.devhouse.core.services

import com.devhouse.core.model.Address
import com.devhouse.core.model.Family
import com.devhouse.core.model.SearchQuery
import com.devhouse.core.model.PagedResult
import com.devhouse.core.model.response.FamilyResponse
import com.devhouse.core.ports.outbound.FamilyRepository
import com.devhouse.core.services.family.SearchFamilyService
import spock.lang.Specification

import java.time.Instant

class SearchFamilyServiceSpec extends Specification {

    FamilyRepository familyRepository = Mock()
    SearchFamilyService service = new SearchFamilyService(familyRepository)

    void "should return paged result of family responses"() {
        given:
        SearchQuery query = new SearchQuery("Silva", 0, 10, "name", "ASC")
        Address address = new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
        List<Family> families = [new Family(1L, "Silva", address, 0, Instant.now(), Instant.now())]
        PagedResult<Family> pagedFamilies = new PagedResult<>(families, 1L, 1, 0, 10)
        familyRepository.findAll(query) >> pagedFamilies

        when:
        PagedResult<FamilyResponse> result = service.execute(query)

        then:
        result.totalElements() == 1L
        result.totalPages() == 1
        result.page() == 0
        result.size() == 10
        result.content().size() == 1
        result.content().get(0).id() == 1L
        result.content().get(0).name() == "Silva"
    }

    void "should return empty result when no families match"() {
        given:
        SearchQuery query = new SearchQuery("Nonexistent", 0, 10, "name", "ASC")
        PagedResult<Family> pagedFamilies = new PagedResult<>([], 0L, 0, 0, 10)
        familyRepository.findAll(query) >> pagedFamilies

        when:
        PagedResult<FamilyResponse> result = service.execute(query)

        then:
        result.totalElements() == 0L
        result.content().isEmpty()
    }
}
