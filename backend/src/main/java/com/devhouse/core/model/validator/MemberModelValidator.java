package com.devhouse.core.model.validator;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Member;

public class MemberModelValidator {

    public void validate(Member member) {
        if (member.getName() == null || member.getName().isBlank()) {
            throw new DomainException("Member name is required");
        }
    }
}
