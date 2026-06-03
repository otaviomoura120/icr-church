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

class UpdateMemberServiceSpec extends Specification {

    MemberRepository memberRepository = Mock()
    FamilyRepository familyRepository = Mock()
    CellChurchRepository cellChurchRepository = Mock()
    UpdateMemberService service = new UpdateMemberService(memberRepository, familyRepository, cellChurchRepository)

    void "should update member and return response"() {
        given:
        Instant createdAt = Instant.parse("2024-01-01T00:00:00Z")
        def existing = buildMember(id: 1L, name: "John", createdDate: createdAt)
        def input = buildMember(id: 1L, name: "John Updated", version: 0)
        def updated = buildMember(id: 1L, name: "John Updated", version: 1, createdDate: createdAt)
        memberRepository.findById(1L) >> Optional.of(existing)
        memberRepository.update(_ as Member) >> updated

        when:
        MemberResponse result = service.execute(input)

        then:
        result.id() == 1L
        result.name() == "John Updated"
    }

    void "should preserve original createdDate on update"() {
        given:
        Instant originalCreatedAt = Instant.parse("2024-01-01T00:00:00Z")
        def existing = buildMember(id: 1L, name: "John", createdDate: originalCreatedAt)
        def input = buildMember(id: 1L, name: "John Updated", version: 0)
        memberRepository.findById(1L) >> Optional.of(existing)

        when:
        service.execute(input)

        then:
        1 * memberRepository.update({ Member m -> m.createdDate == originalCreatedAt }) >> { Member m -> m }
    }

    void "should throw exception when name is blank"() {
        when:
        service.execute(buildMember(id: 1L, name: ""))

        then:
        thrown(DomainException)
        0 * memberRepository.update(_)
    }

    void "should throw exception when member not found"() {
        given:
        memberRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(buildMember(id: 99L, name: "John"))

        then:
        thrown(DomainException)
        0 * memberRepository.update(_)
    }

    void "should throw exception when family id does not exist"() {
        given:
        def family = new Family(99L, null, null, null, null, null)
        def input = buildMember(id: 1L, name: "John", family: family)
        familyRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(input)

        then:
        thrown(DomainException)
        0 * memberRepository.update(_)
    }

    void "should throw exception when cell church id does not exist"() {
        given:
        def cellChurch = CellChurch.stub(99L)
        def input = buildMember(id: 1L, name: "John", cellChurch: cellChurch)
        cellChurchRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(input)

        then:
        thrown(DomainException)
        0 * memberRepository.update(_)
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
                args.createdDate as Instant ?: Instant.now(),
                Instant.now()
        )
    }
}
