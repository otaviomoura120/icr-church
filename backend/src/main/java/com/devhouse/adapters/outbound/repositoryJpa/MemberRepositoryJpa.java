package com.devhouse.adapters.outbound.repositoryJpa;

import com.devhouse.adapters.outbound.repositoryJpa.entities.CellChurchEntityJpa;
import com.devhouse.adapters.outbound.repositoryJpa.entities.FamilyEntityJpa;
import com.devhouse.adapters.outbound.repositoryJpa.entities.MemberEntityJpa;
import com.devhouse.core.model.Member;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.ports.outbound.MemberRepository;
import com.devhouse.shared.JpqlUtils;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class MemberRepositoryJpa implements MemberRepository {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "createdAt");

    private final EntityManager entityManager;

    public MemberRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Member save(Member member) {
        MemberEntityJpa entity = MemberEntityJpa.from(member);
        resolveAssociations(entity, member);
        entityManager.persist(entity);
        return entity.toModel();
    }

    @Override
    @Transactional
    public Member update(Member member) {
        MemberEntityJpa entity = MemberEntityJpa.from(member);
        resolveAssociations(entity, member);
        MemberEntityJpa merged = entityManager.merge(entity);
        return merged.toModel();
    }

    private void resolveAssociations(MemberEntityJpa entity, Member member) {
        if (member.getFamily() != null && member.getFamily().getId() != null) {
            entity.setFamily(entityManager.getReference(FamilyEntityJpa.class, member.getFamily().getId()));
        }
        if (member.getCellChurch() != null && member.getCellChurch().getId() != null) {
            entity.setCellChurch(entityManager.getReference(CellChurchEntityJpa.class, member.getCellChurch().getId()));
        }
    }

    @Override
    @ReadOnly
    public Optional<Member> findById(Long id) {
        MemberEntityJpa entity = entityManager.find(MemberEntityJpa.class, id);
        return Optional.ofNullable(entity).map(MemberEntityJpa::toModel);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MemberEntityJpa entity = entityManager.find(MemberEntityJpa.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    @ReadOnly
    public PagedResult<Member> findAll(SearchQuery query) {
        String sortBy = ALLOWED_SORT_FIELDS.contains(query.sortBy()) ? query.sortBy() : "name";
        String sortDirection = "DESC".equalsIgnoreCase(query.sortDirection()) ? "DESC" : "ASC";

        String escapedSearch = JpqlUtils.escapeLike(query.search());

        String jpql = "SELECT m FROM MemberEntityJpa m WHERE (:search IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY m." + sortBy + " " + sortDirection;
        String countJpql = "SELECT COUNT(m) FROM MemberEntityJpa m WHERE (:search IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%')))";

        List<MemberEntityJpa> entities = entityManager.createQuery(jpql, MemberEntityJpa.class)
                .setParameter("search", escapedSearch)
                .setFirstResult(query.page() * query.size())
                .setMaxResults(query.size())
                .getResultList();

        Long totalElements = entityManager.createQuery(countJpql, Long.class)
                .setParameter("search", escapedSearch)
                .getSingleResult();

        List<Member> members = entities.stream()
                .map(MemberEntityJpa::toModel)
                .toList();

        int totalPages = query.size() > 0 ? (int) Math.ceil((double) totalElements / query.size()) : 0;

        return new PagedResult<>(members, totalElements, totalPages, query.page(), query.size());
    }
}
