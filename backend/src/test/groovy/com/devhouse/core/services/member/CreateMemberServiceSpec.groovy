package com.devhouse.core.services.member

import com.devhouse.config.exception.DomainException
import com.devhouse.core.model.CellChurch
import com.devhouse.core.model.Family
import com.devhouse.core.model.Member
import com.devhouse.core.model.response.MemberResponse
import com.devhouse.core.ports.outbound.CellChurchRepository
import com.devhouse.core.ports.outbound.FamilyRepository
import com.devhouse.core.ports.outbound.MemberRepository
import spock.lang.Specification

import java.time.Instant

class CreateMemberServiceSpec extends Specification {

    MemberRepository memberRepository = Mock()
    FamilyRepository familyRepository = Mock()
    CellChurchRepository cellChurchRepository = Mock()
    CreateMemberService service = new CreateMemberService(memberRepository, familyRepository, cellChurchRepository)

    void "should save member and return response"() {
        given:
        def input = buildMember(name: "John")
        def saved = buildMember(id: 1L, name: "John")
        memberRepository.save(input) >> saved

        when:
        MemberResponse result = service.execute(input)

        then:
        result.id() == 1L
        result.name() == "John"
    }

    void "should throw exception when name is blank"() {
        when:
        service.execute(buildMember(name: ""))

        then:
        thrown(DomainException)
        0 * memberRepository.save(_)
    }

    void "should throw exception when name is null"() {
        when:
        service.execute(buildMember(name: null))

        then:
        thrown(DomainException)
        0 * memberRepository.save(_)
    }

    void "should throw exception when family id does not exist"() {
        given:
        def family = new Family(99L, null, null, null, null, null)
        def input = buildMember(name: "John", family: family)
        familyRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(input)

        then:
        thrown(DomainException)
        0 * memberRepository.save(_)
    }

    void "should throw exception when cell church id does not exist"() {
        given:
        def cellChurch = CellChurch.stub(99L)
        def input = buildMember(name: "John", cellChurch: cellChurch)
        cellChurchRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(input)

        then:
        thrown(DomainException)
        0 * memberRepository.save(_)
    }

    void "should save member with valid family and cell church"() {
        given:
        def family = new Family(1L, null, null, null, null, null)
        def cellChurch = CellChurch.stub(2L)
        def input = buildMember(name: "John", family: family, cellChurch: cellChurch)
        def saved = buildMember(id: 10L, name: "John", family: family, cellChurch: cellChurch)
        familyRepository.findById(1L) >> Optional.of(family)
        cellChurchRepository.findById(2L) >> Optional.of(cellChurch)
        memberRepository.save(input) >> saved

        when:
        MemberResponse result = service.execute(input)

        then:
        result.id() == 10L
        result.familyId() == 1L
        result.cellChurchId() == 2L
    }

    private static Member buildMember(Map args = [:]) {
        new Member(
                args.id as Long,
                args.family as Family,
                args.cellChurch as CellChurch,
                (args.version ?: 0) as Integer,
                args.name as String,
                null, null, null, null, null, null, null, null, null,
                true, false, null, null,
                Instant.now(), Instant.now()
        )
    }
}
