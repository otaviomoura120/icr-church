package com.devhouse.adapters.outbound.repositoryJpa;

import com.devhouse.adapters.outbound.repositoryJpa.entities.FamilyEntityJpa;
import com.devhouse.core.model.Family;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.ports.outbound.FamilyRepository;
import com.devhouse.shared.JpqlUtils;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class FamilyRepositoryJpa implements FamilyRepository {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "createdAt");

    private final EntityManager entityManager;

    public FamilyRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Family save(Family family) {
        FamilyEntityJpa familyEntityJpa = FamilyEntityJpa.from(family);
        entityManager.persist(familyEntityJpa);
        return familyEntityJpa.toModel();
    }

    @Override
    @Transactional
    public Family update(Family family) {
        FamilyEntityJpa familyEntityJpa = FamilyEntityJpa.from(family);
        FamilyEntityJpa merged = entityManager.merge(familyEntityJpa);
        return merged.toModel();
    }

    @Override
    @ReadOnly
    public Optional<Family> findById(Long id) {
        FamilyEntityJpa familyEntityJpa = entityManager.find(FamilyEntityJpa.class, id);
        return Optional.ofNullable(familyEntityJpa).map(FamilyEntityJpa::toModel);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FamilyEntityJpa entity = entityManager.find(FamilyEntityJpa.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    @ReadOnly
    public PagedResult<Family> findAll(SearchQuery query) {
        String sortBy = ALLOWED_SORT_FIELDS.contains(query.sortBy()) ? query.sortBy() : "name";
        String sortDirection = "DESC".equalsIgnoreCase(query.sortDirection()) ? "DESC" : "ASC";

        String escapedSearch = JpqlUtils.escapeLike(query.search());

        String jpql = "SELECT f FROM FamilyEntityJpa f WHERE (:search IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY f." + sortBy + " " + sortDirection;
        String countJpql = "SELECT COUNT(f) FROM FamilyEntityJpa f WHERE (:search IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%')))";

        List<FamilyEntityJpa> entities = entityManager.createQuery(jpql, FamilyEntityJpa.class)
                .setParameter("search", escapedSearch)
                .setFirstResult(query.page() * query.size())
                .setMaxResults(query.size())
                .getResultList();

        Long totalElements = entityManager.createQuery(countJpql, Long.class)
                .setParameter("search", escapedSearch)
                .getSingleResult();

        List<Family> families = entities.stream()
                .map(FamilyEntityJpa::toModel)
                .toList();

        int totalPages = query.size() > 0 ? (int) Math.ceil((double) totalElements / query.size()) : 0;

        return new PagedResult<>(families, totalElements, totalPages, query.page(), query.size());
    }
}
