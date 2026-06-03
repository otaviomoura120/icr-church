package com.devhouse.adapters.outbound.repositoryJpa;

import com.devhouse.adapters.outbound.repositoryJpa.entities.CellChurchEntityJpa;
import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;
import com.devhouse.core.ports.outbound.CellChurchRepository;
import com.devhouse.shared.JpqlUtils;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class CellChurchRepositoryJpa implements CellChurchRepository {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "createdAt");

    private final EntityManager entityManager;

    public CellChurchRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CellChurch save(CellChurch cellChurch) {
        CellChurchEntityJpa entity = CellChurchEntityJpa.from(cellChurch);
        entityManager.persist(entity);
        return entity.toModel();
    }

    @Override
    @Transactional
    public CellChurch update(CellChurch cellChurch) {
        CellChurchEntityJpa entity = CellChurchEntityJpa.from(cellChurch);
        CellChurchEntityJpa merged = entityManager.merge(entity);
        return merged.toModel();
    }

    @Override
    @ReadOnly
    public Optional<CellChurch> findById(Long id) {
        CellChurchEntityJpa entity = entityManager.find(CellChurchEntityJpa.class, id);
        return Optional.ofNullable(entity).map(CellChurchEntityJpa::toModel);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CellChurchEntityJpa entity = entityManager.find(CellChurchEntityJpa.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    @ReadOnly
    public PagedResult<CellChurch> findAll(SearchQuery query) {
        String sortBy = ALLOWED_SORT_FIELDS.contains(query.sortBy()) ? query.sortBy() : "name";
        String sortDirection = "DESC".equalsIgnoreCase(query.sortDirection()) ? "DESC" : "ASC";

        String escapedSearch = JpqlUtils.escapeLike(query.search());

        String jpql = "SELECT c FROM CellChurchEntityJpa c WHERE (:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY c." + sortBy + " " + sortDirection;
        String countJpql = "SELECT COUNT(c) FROM CellChurchEntityJpa c WHERE (:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))";

        List<CellChurchEntityJpa> entities = entityManager.createQuery(jpql, CellChurchEntityJpa.class)
                .setParameter("search", escapedSearch)
                .setFirstResult(query.page() * query.size())
                .setMaxResults(query.size())
                .getResultList();

        Long totalElements = entityManager.createQuery(countJpql, Long.class)
                .setParameter("search", escapedSearch)
                .getSingleResult();

        List<CellChurch> results = entities.stream()
                .map(CellChurchEntityJpa::toModel)
                .toList();

        int totalPages = query.size() > 0 ? (int) Math.ceil((double) totalElements / query.size()) : 0;

        return new PagedResult<>(results, totalElements, totalPages, query.page(), query.size());
    }
}
