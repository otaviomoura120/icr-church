package com.devhouse.core.services.member

import com.devhouse.core.model.Member
import com.devhouse.core.model.PagedResult
import com.devhouse.core.model.SearchQuery
import com.devhouse.core.model.response.MemberResponse
import com.devhouse.core.ports.outbound.MemberRepository
import spock.lang.Specification

import java.time.Instant

class SearchMemberServiceSpec extends Specification {

    MemberRepository memberRepository = Mock()
    SearchMemberService service = new SearchMemberService(memberRepository)

    void "should return paged result of member responses"() {
        given:
        def query = new SearchQuery("John", 0, 10, "name", "ASC")
        def members = [buildMember(id: 1L, name: "John")]
        memberRepository.findAll(query) >> new PagedResult<>(members, 1L, 1, 0, 10)

        when:
        PagedResult<MemberResponse> result = service.execute(query)

        then:
        result.totalElements() == 1L
        result.totalPages() == 1
        result.page() == 0
        result.size() == 10
        result.content().size() == 1
        result.content().get(0).id() == 1L
        result.content().get(0).name() == "John"
    }

    void "should return empty result when no members match"() {
        given:
        def query = new SearchQuery("Nonexistent", 0, 10, "name", "ASC")
        memberRepository.findAll(query) >> new PagedResult<>([], 0L, 0, 0, 10)

        when:
        PagedResult<MemberResponse> result = service.execute(query)

        then:
        result.totalElements() == 0L
        result.content().isEmpty()
    }

    private static Member buildMember(Map args = [:]) {
        new Member(
                args.id as Long,
                null, null, 0,
                args.name as String,
                null, null, null, null, null, null, null, null, null,
                true, false, null, null,
                Instant.now(), Instant.now()
        )
    }
}
