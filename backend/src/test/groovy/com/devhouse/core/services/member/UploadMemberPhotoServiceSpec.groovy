package com.devhouse.core.services.member

import com.devhouse.config.exception.DomainException
import com.devhouse.core.model.Member
import com.devhouse.core.model.response.MemberResponse
import com.devhouse.core.ports.outbound.FileStoragePort
import com.devhouse.core.ports.outbound.MemberRepository
import spock.lang.Specification

import java.time.Instant

class UploadMemberPhotoServiceSpec extends Specification {

    MemberRepository memberRepository = Mock()
    FileStoragePort fileStoragePort = Mock()
    UploadMemberPhotoService service = new UploadMemberPhotoService(memberRepository, fileStoragePort)

    void "should save photo and update member profile photo path"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: null)
        byte[] photoData = "image-bytes".bytes
        memberRepository.findById(1L) >> Optional.of(existing)
        memberRepository.update(_ as Member) >> { Member m -> m }

        when:
        MemberResponse result = service.execute(1L, photoData, "avatar.jpg")

        then:
        1 * fileStoragePort.save("files/profile-photos/1/avatar.jpg", photoData)
        result.profilePhoto() == "files/profile-photos/1/avatar.jpg"
    }

    void "should delete old photo before saving new one"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: "files/profile-photos/1/old.jpg")
        byte[] photoData = "new-image-bytes".bytes
        memberRepository.findById(1L) >> Optional.of(existing)
        memberRepository.update(_ as Member) >> { Member m -> m }

        when:
        service.execute(1L, photoData, "new.jpg")

        then:
        1 * fileStoragePort.delete("files/profile-photos/1/old.jpg")
        1 * fileStoragePort.save("files/profile-photos/1/new.jpg", photoData)
    }

    void "should not delete old photo when member has no previous photo"() {
        given:
        def existing = buildMember(id: 1L, profilePhoto: null)
        memberRepository.findById(1L) >> Optional.of(existing)
        memberRepository.update(_ as Member) >> { Member m -> m }

        when:
        service.execute(1L, "bytes".bytes, "photo.jpg")

        then:
        0 * fileStoragePort.delete(_)
    }

    void "should throw exception when member not found"() {
        given:
        memberRepository.findById(99L) >> Optional.empty()

        when:
        service.execute(99L, "bytes".bytes, "photo.jpg")

        then:
        thrown(DomainException)
        0 * fileStoragePort.save(_, _)
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
