package com.devhouse.core.services.cellchurch

import com.devhouse.core.ports.outbound.CellChurchRepository
import spock.lang.Specification

class DeleteCellChurchServiceSpec extends Specification {

    CellChurchRepository cellChurchRepository = Mock()
    DeleteCellChurchService service = new DeleteCellChurchService(cellChurchRepository)

    void "should delegate deletion to repository"() {
        given:
        Long id = 1L

        when:
        service.execute(id)

        then:
        1 * cellChurchRepository.delete(id)
    }
}
