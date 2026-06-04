package com.devhouse.core.services.cellchurch

import com.devhouse.config.exception.DomainException
import com.devhouse.core.ports.outbound.CellChurchRepository
import com.devhouse.core.ports.outbound.MemberRepository
import spock.lang.Specification

class DeleteCellChurchServiceSpec extends Specification {

    CellChurchRepository cellChurchRepository = Mock()
    MemberRepository memberRepository = Mock()
    DeleteCellChurchService service = new DeleteCellChurchService(cellChurchRepository, memberRepository)

    void "should delegate deletion to repository when no members are linked"() {
        given:
        Long id = 1L
        memberRepository.countByCellChurchId(id) >> 0

        when:
        service.execute(id)

        then:
        1 * cellChurchRepository.delete(id)
    }

    void "should throw DomainException when cell church has linked members"() {
        given:
        Long id = 1L
        memberRepository.countByCellChurchId(id) >> 3

        when:
        service.execute(id)

        then:
        thrown(DomainException)
        0 * cellChurchRepository.delete(_)
    }
}
