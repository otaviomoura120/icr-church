package com.devhouse.adapters.outbound.repositoryJpa;

import com.devhouse.adapters.outbound.repositoryJpa.entities.FamilyEntityJpa;
import com.devhouse.core.model.Family;
import com.devhouse.core.ports.outbound.FamilyRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.Optional;

@Singleton
public class FamilyRepositoryJpa implements FamilyRepository {

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
        entityManager.merge(familyEntityJpa);
        return familyEntityJpa.toModel();
    }

    @Override
    @ReadOnly
    public Optional<Family> findById(Long id) {
        FamilyEntityJpa familyEntityJpa = entityManager.find(FamilyEntityJpa.class, id);
        return Optional.of(familyEntityJpa.toModel());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
