package com.devhouse.core.services.cellchurch

import com.devhouse.config.exception.DomainException
import com.devhouse.core.model.Address
import com.devhouse.core.model.CellChurch
import com.devhouse.core.model.CellProfile
import com.devhouse.core.model.response.CellChurchResponse
import com.devhouse.core.ports.outbound.CellChurchRepository
import spock.lang.Specification

import java.time.Instant
import java.time.OffsetTime
import java.time.ZoneOffset

class UpdateCellChurchServiceSpec extends Specification {

    CellChurchRepository cellChurchRepository = Mock()
    UpdateCellChurchService service = new UpdateCellChurchService(cellChurchRepository)

    def address() {
        new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
    }

    def cellProfile(Long id = 1L) {
        new CellProfile(id, 0, "Profile", Instant.now(), Instant.now())
    }

    void "should update cell church and return response"() {
        given:
        Instant createdAt = Instant.parse("2024-01-01T00:00:00Z")
        OffsetTime hour = OffsetTime.of(19, 0, 0, 0, ZoneOffset.UTC)
        CellChurch cellChurch = new CellChurch(1L, 0, cellProfile(), address(), "Célula Norte Updated", "TUESDAY", hour, Instant.now(), Instant.now())
        CellChurch existing = new CellChurch(1L, 0, cellProfile(), address(), "Célula Norte", "MONDAY", hour, createdAt, createdAt)
        CellChurch updated = new CellChurch(1L, 1, cellProfile(), address(), "Célula Norte Updated", "TUESDAY", hour, createdAt, Instant.now())
        cellChurchRepository.findById(1L) >> Optional.of(existing)
        cellChurchRepository.update(_ as CellChurch) >> updated

        when:
        CellChurchResponse result = service.execute(cellChurch)

        then:
        result.id() == 1L
        result.name() == "Célula Norte Updated"
    }

    void "should throw exception when name is blank"() {
        given:
        CellChurch cellChurch = new CellChurch(1L, 0, cellProfile(), address(), "", "MONDAY", null, Instant.now(), Instant.now())

        when:
        service.execute(cellChurch)

        then:
        thrown(DomainException)
    }

    void "should throw exception when cell church not found"() {
        given:
        CellChurch cellChurch = new CellChurch(99L, 0, cellProfile(), address(), "Célula Norte", "MONDAY", null, Instant.now(), Instant.now())
        cellChurchRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(cellChurch)

        then:
        thrown(DomainException)
    }
}
