package com.devhouse.core.services.member

import com.devhouse.config.exception.DomainException
import com.devhouse.core.model.Member
import com.devhouse.core.ports.outbound.FileStoragePort
import com.devhouse.core.ports.outbound.MemberRepository
import spock.lang.Specification

import java.time.Instant

class DeleteMemberPhotoServiceSpec extends Specification {

    MemberRepository memberRepository = Mock()
    FileStoragePort fileStoragePort = Mock()
    DeleteMemberPhotoService service = new DeleteMemberPhotoService(memberRepository, fileStoragePort)

    void "should delete photo file and clear profile photo path"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: "files/profile-photos/1/avatar.jpg")
        memberRepository.findById(1L) >> Optional.of(existing)

        when:
        service.execute(1L)

        then:
        1 * fileStoragePort.delete("files/profile-photos/1/avatar.jpg")
        1 * memberRepository.update({ Member m -> m.profilePhoto == null }) >> { Member m -> m }
    }

    void "should do nothing when member has no photo"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: null)
        memberRepository.findById(1L) >> Optional.of(existing)

        when:
        service.execute(1L)

        then:
        0 * fileStoragePort.delete(_)
        0 * memberRepository.update(_)
    }

    void "should throw exception when member not found"() {
        given:
        memberRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(99L)

        then:
        thrown(DomainException)
        0 * fileStoragePort.delete(_)
        0 * memberRepository.update(_)
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
