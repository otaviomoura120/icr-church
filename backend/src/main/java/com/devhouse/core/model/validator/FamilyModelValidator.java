package com.devhouse.core.model.validator;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.Family;

public class FamilyModelValidator {

    public void validate(Family family) {
        if (family.getName() == null || "".equals(family.getName())) {
            throw new DomainException("Family name is null or empty");
        }

        if (family.getAddress() == null) {
            throw new DomainException("Address is null");
        }
    }

}
