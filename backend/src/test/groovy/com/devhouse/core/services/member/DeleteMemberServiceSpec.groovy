package com.devhouse.core.services.member

import com.devhouse.core.model.Member
import com.devhouse.core.ports.outbound.FileStoragePort
import com.devhouse.core.ports.outbound.MemberRepository
import spock.lang.Specification

import java.time.Instant

class DeleteMemberServiceSpec extends Specification {

    MemberRepository memberRepository = Mock()
    FileStoragePort fileStoragePort = Mock()
    DeleteMemberService service = new DeleteMemberService(memberRepository, fileStoragePort)

    void "should delete member and its photo when photo exists"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: "files/profile-photos/1/photo.jpg")
        memberRepository.findById(1L) >> Optional.of(existing)

        when:
        service.execute(1L)

        then:
        1 * fileStoragePort.delete("files/profile-photos/1/photo.jpg")
        1 * memberRepository.delete(1L)
    }

    void "should delete member without touching file storage when no photo"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: null)
        memberRepository.findById(1L) >> Optional.of(existing)

        when:
        service.execute(1L)

        then:
        0 * fileStoragePort.delete(_)
        1 * memberRepository.delete(1L)
    }

    void "should still delete member from repository when member not found in pre-check"() {
        given:
        memberRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(99L)

        then:
        0 * fileStoragePort.delete(_)
        1 * memberRepository.delete(99L)
    }

    private static Member buildMember(Map args = [:]) {
        new Member(
                args.id as Long,
                null, null, 0,
                "John",
                null, null,
                args.profilePhoto as String,
                null, null, null, null, null, null,
                true, false, null, null,
                Instant.now(), Instant.now()
        )
    }
}
