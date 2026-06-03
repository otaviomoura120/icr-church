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

class CreateCellChurchServiceSpec extends Specification {

    CellChurchRepository cellChurchRepository = Mock()
    CreateCellChurchService service = new CreateCellChurchService(cellChurchRepository)

    def address() {
        new Address("Street", "12345", "BR", "SP", "City", "Neighborhood")
    }

    def cellProfile(Long id = 1L) {
        new CellProfile(id, 0, "Profile", Instant.now(), Instant.now())
    }

    void "should save cell church and return response"() {
        given:
        CellChurch cellChurch = new CellChurch("Célula Norte", cellProfile(), address(), "MONDAY", OffsetTime.of(19, 0, 0, 0, ZoneOffset.UTC))
        CellChurch saved = new CellChurch(1L, 0, cellProfile(), address(), "Célula Norte", "MONDAY", OffsetTime.of(19, 0, 0, 0, ZoneOffset.UTC), Instant.now(), Instant.now())
        cellChurchRepository.save(cellChurch) >> saved

        when:
        CellChurchResponse result = service.execute(cellChurch)

        then:
        result.id() == 1L
        result.name() == "Célula Norte"
    }

    void "should throw exception when name is blank"() {
        given:
        CellChurch cellChurch = new CellChurch("", cellProfile(), address(), "MONDAY", null)

        when:
        service.execute(cellChurch)

        then:
        thrown(DomainException)
    }

    void "should throw exception when name is null"() {
        given:
        CellChurch cellChurch = new CellChurch(null, cellProfile(), address(), "MONDAY", null)

        when:
        service.execute(cellChurch)

        then:
        thrown(DomainException)
    }

    void "should throw exception when cellProfile is null"() {
        given:
        CellChurch cellChurch = new CellChurch("Célula Norte", null, address(), "MONDAY", null)

        when:
        service.execute(cellChurch)

        then:
        thrown(DomainException)
    }
}
