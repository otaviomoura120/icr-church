package com.devhouse.core.services.cellchurch;

import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.response.CellChurchResponse;
import com.devhouse.core.model.validator.CellChurchModelValidator;
import com.devhouse.core.ports.inbound.cellchurch.CreateCellChurchInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;

public class CreateCellChurchService implements CreateCellChurchInboundPort {

    private final CellChurchRepository cellChurchRepository;
    private final CellChurchModelValidator validator = new CellChurchModelValidator();

    public CreateCellChurchService(CellChurchRepository cellChurchRepository) {
        this.cellChurchRepository = cellChurchRepository;
    }

    @Override
    public CellChurchResponse execute(CellChurch cellChurch) {
        validator.validate(cellChurch);
        CellChurch saved = cellChurchRepository.save(cellChurch);
        return saved.toResponse();
    }
}
