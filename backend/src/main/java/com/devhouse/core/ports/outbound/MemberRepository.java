package com.devhouse.core.ports.outbound;

import com.devhouse.core.model.Member;

public interface MemberRepository {

    Member save(Member member);
    Member update(Member member);
    Member findById(Integer id);
    void delete(Member member);
}
