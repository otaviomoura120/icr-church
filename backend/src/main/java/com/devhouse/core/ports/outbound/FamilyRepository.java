package com.devhouse.core.ports.outbound;

import com.devhouse.core.model.Family;

import java.util.Optional;

public interface FamilyRepository {
    Family save(Family family);
    Family update(Family family);
    Optional<Family> findById(Long id);
    void delete(Long id);
}
