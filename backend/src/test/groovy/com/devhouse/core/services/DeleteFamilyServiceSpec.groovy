package com.devhouse.core.services

import com.devhouse.config.exception.DomainException
import com.devhouse.core.ports.outbound.FamilyRepository
import com.devhouse.core.ports.outbound.MemberRepository
import com.devhouse.core.services.family.DeleteFamilyService
import spock.lang.Specification

class DeleteFamilyServiceSpec extends Specification {

    FamilyRepository familyRepository = Mock()
    MemberRepository memberRepository = Mock()
    DeleteFamilyService service = new DeleteFamilyService(familyRepository, memberRepository)

    void "should delegate deletion to repository when no members are linked"() {
        given:
        Long id = 1L
        memberRepository.countByFamilyId(id) >> 0

        when:
        service.execute(id)

        then:
        1 * familyRepository.delete(id)
    }

    void "should throw DomainException when family has linked members"() {
        given:
        Long id = 1L
        memberRepository.countByFamilyId(id) >> 2

        when:
        service.execute(id)

        then:
        thrown(DomainException)
        0 * familyRepository.delete(_)
    }
}
