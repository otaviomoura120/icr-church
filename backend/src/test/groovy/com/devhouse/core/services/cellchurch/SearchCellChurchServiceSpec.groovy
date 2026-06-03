package com.devhouse.core.services.cellchurch

import com.devhouse.core.model.Address
import com.devhouse.core.model.CellChurch
import com.devhouse.core.model.CellProfile
import com.devhouse.core.model.PagedResult
import com.devhouse.core.model.SearchQuery
import com.devhouse.core.model.response.CellChurchResponse
import com.devhouse.core.ports.outbound.CellChurchRepository
import spock.lang.Specification

import java.time.Instant
import java.time.OffsetTime
import java.time.ZoneOffset

class SearchCellChurchServiceSpec extends Specification {

    CellChurchRepository cellChurchRepository = Mock()
    SearchCellChurchService service = new SearchCellChurchService(cellChurchRepository)

    def address() {
        new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
    }

    def cellProfile(Long id = 1L) {
        new CellProfile(id, 0, "Profile", Instant.now(), Instant.now())
    }

    void "should return paged result of cell church responses"() {
        given:
        SearchQuery query = new SearchQuery("Norte", 0, 10, "name", "ASC")
        OffsetTime hour = OffsetTime.of(19, 0, 0, 0, ZoneOffset.UTC)
        List<CellChurch> cellChurches = [new CellChurch(1L, 0, cellProfile(), address(), "Célula Norte", "MONDAY", hour, Instant.now(), Instant.now())]
        PagedResult<CellChurch> paged = new PagedResult<>(cellChurches, 1L, 1, 0, 10)
        cellChurchRepository.findAll(query) >> paged

        when:
        PagedResult<CellChurchResponse> result = service.execute(query)

        then:
        result.totalElements() == 1L
        result.totalPages() == 1
        result.page() == 0
        result.size() == 10
        result.content().size() == 1
        result.content().get(0).id() == 1L
        result.content().get(0).name() == "Célula Norte"
    }

    void "should return empty result when no cell churches match"() {
        given:
        SearchQuery query = new SearchQuery("Nonexistent", 0, 10, "name", "ASC")
        PagedResult<CellChurch> paged = new PagedResult<>([], 0L, 0, 0, 10)
        cellChurchRepository.findAll(query) >> paged

        when:
        PagedResult<CellChurchResponse> result = service.execute(query)

        then:
        result.totalElements() == 0L
        result.content().isEmpty()
    }
}
