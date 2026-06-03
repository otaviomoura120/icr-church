package com.devhouse.core.model.validator;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.CellChurch;

public class CellChurchModelValidator {

    public void validate(CellChurch cellChurch) {
        if (cellChurch.getName() == null || "".equals(cellChurch.getName())) {
            throw new DomainException("CellChurch name is null or empty");
        }

        if (cellChurch.getCellProfile() == null || cellChurch.getCellProfile().getId() == null) {
            throw new DomainException("CellProfile is null or has no id");
        }
    }
}
