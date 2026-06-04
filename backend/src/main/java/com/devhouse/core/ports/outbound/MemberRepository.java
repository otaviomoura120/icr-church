package com.devhouse.core.ports.outbound;

import com.devhouse.core.model.Member;
import com.devhouse.core.model.PagedResult;
import com.devhouse.core.model.SearchQuery;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Member update(Member member);
    Optional<Member> findById(Long id);
    void delete(Long id);
    PagedResult<Member> findAll(SearchQuery query);
    long countByFamilyId(Long familyId);
    long countByCellChurchId(Long cellChurchId);
}
