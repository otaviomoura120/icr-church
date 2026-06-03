package com.devhouse.config;

import com.devhouse.adapters.inbound.http.converter.CellChurchConverter;
import com.devhouse.adapters.inbound.http.converter.FamilyConverter;
import com.devhouse.adapters.inbound.http.converter.MemberConverter;
import com.devhouse.core.ports.inbound.cellchurch.CreateCellChurchInboundPort;
import com.devhouse.core.ports.inbound.cellchurch.DeleteCellChurchInboundPort;
import com.devhouse.core.ports.inbound.cellchurch.SearchCellChurchInboundPort;
import com.devhouse.core.ports.inbound.cellchurch.UpdateCellChurchInboundPort;
import com.devhouse.core.ports.inbound.family.CreateFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.DeleteFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.SearchFamilyInboundPort;
import com.devhouse.core.ports.inbound.family.UpdateFamilyInboundPort;
import com.devhouse.core.ports.inbound.member.CreateMemberInboundPort;
import com.devhouse.core.ports.inbound.member.DeleteMemberInboundPort;
import com.devhouse.core.ports.inbound.member.DeleteMemberPhotoInboundPort;
import com.devhouse.core.ports.inbound.member.SearchMemberInboundPort;
import com.devhouse.core.ports.inbound.member.UpdateMemberInboundPort;
import com.devhouse.core.ports.inbound.member.UploadMemberPhotoInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.core.ports.outbound.FileStoragePort;
import com.devhouse.core.ports.outbound.MemberRepository;
import com.devhouse.core.services.cellchurch.CreateCellChurchService;
import com.devhouse.core.services.cellchurch.DeleteCellChurchService;
import com.devhouse.core.services.cellchurch.SearchCellChurchService;
import com.devhouse.core.services.cellchurch.UpdateCellChurchService;
import com.devhouse.core.services.family.CreateFamilyService;
import com.devhouse.core.services.family.DeleteFamilyService;
import com.devhouse.core.services.family.SearchFamilyService;
import com.devhouse.core.services.family.UpdateFamilyService;
import com.devhouse.core.services.member.CreateMemberService;
import com.devhouse.core.services.member.DeleteMemberPhotoService;
import com.devhouse.core.services.member.DeleteMemberService;
import com.devhouse.core.services.member.SearchMemberService;
import com.devhouse.core.services.member.UpdateMemberService;
import com.devhouse.core.services.member.UploadMemberPhotoService;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
public class BeanFactory {

    @Bean
    public CreateFamilyInboundPort createFamilyInboundPort(FamilyRepository familyRepository) {
        return new CreateFamilyService(familyRepository);
    }

    @Bean
    public UpdateFamilyInboundPort updateFamilyInboundPort(FamilyRepository familyRepository) {
        return new UpdateFamilyService(familyRepository);
    }

    @Bean
    public DeleteFamilyInboundPort deleteFamilyInboundPort(FamilyRepository familyRepository) {
        return new DeleteFamilyService(familyRepository);
    }

    @Bean
    public SearchFamilyInboundPort searchFamilyInboundPort(FamilyRepository familyRepository) {
        return new SearchFamilyService(familyRepository);
    }

    @Bean
    public FamilyConverter familyConverter() {
        return new FamilyConverter();
    }

    @Bean
    public CreateFamilyService createFamilyService(FamilyRepository familyRepository) {
        return new CreateFamilyService(familyRepository);
    }

    @Bean
    public CreateCellChurchInboundPort createCellChurchInboundPort(CellChurchRepository cellChurchRepository) {
        return new CreateCellChurchService(cellChurchRepository);
    }

    @Bean
    public UpdateCellChurchInboundPort updateCellChurchInboundPort(CellChurchRepository cellChurchRepository) {
        return new UpdateCellChurchService(cellChurchRepository);
    }

    @Bean
    public DeleteCellChurchInboundPort deleteCellChurchInboundPort(CellChurchRepository cellChurchRepository) {
        return new DeleteCellChurchService(cellChurchRepository);
    }

    @Bean
    public SearchCellChurchInboundPort searchCellChurchInboundPort(CellChurchRepository cellChurchRepository) {
        return new SearchCellChurchService(cellChurchRepository);
    }

    @Bean
    public CellChurchConverter cellChurchConverter() {
        return new CellChurchConverter();
    }

    @Bean
    public CreateMemberInboundPort createMemberInboundPort(MemberRepository memberRepository,
                                                            FamilyRepository familyRepository,
                                                            CellChurchRepository cellChurchRepository) {
        return new CreateMemberService(memberRepository, familyRepository, cellChurchRepository);
    }

    @Bean
    public UpdateMemberInboundPort updateMemberInboundPort(MemberRepository memberRepository,
                                                            FamilyRepository familyRepository,
                                                            CellChurchRepository cellChurchRepository) {
        return new UpdateMemberService(memberRepository, familyRepository, cellChurchRepository);
    }

    @Bean
    public DeleteMemberInboundPort deleteMemberInboundPort(MemberRepository memberRepository,
                                                            FileStoragePort fileStoragePort) {
        return new DeleteMemberService(memberRepository, fileStoragePort);
    }

    @Bean
    public SearchMemberInboundPort searchMemberInboundPort(MemberRepository memberRepository) {
        return new SearchMemberService(memberRepository);
    }

    @Bean
    public UploadMemberPhotoInboundPort uploadMemberPhotoInboundPort(MemberRepository memberRepository,
                                                                      FileStoragePort fileStoragePort) {
        return new UploadMemberPhotoService(memberRepository, fileStoragePort);
    }

    @Bean
    public DeleteMemberPhotoInboundPort deleteMemberPhotoInboundPort(MemberRepository memberRepository,
                                                                      FileStoragePort fileStoragePort) {
        return new DeleteMemberPhotoService(memberRepository, fileStoragePort);
    }

    @Bean
    public MemberConverter memberConverter() {
        return new MemberConverter();
    }
}
