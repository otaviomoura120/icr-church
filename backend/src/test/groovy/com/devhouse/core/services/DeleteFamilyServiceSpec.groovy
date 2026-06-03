package com.devhouse.core.services

import com.devhouse.core.ports.outbound.FamilyRepository
import com.devhouse.core.services.family.DeleteFamilyService
import spock.lang.Specification

class DeleteFamilyServiceSpec extends Specification {

    FamilyRepository familyRepository = Mock()
    DeleteFamilyService service = new DeleteFamilyService(familyRepository)

    void "should delegate deletion to repository"() {
        given:
        Long id = 1L

        when:
        service.execute(id)

        then:
        1 * familyRepository.delete(id)
    }
}
