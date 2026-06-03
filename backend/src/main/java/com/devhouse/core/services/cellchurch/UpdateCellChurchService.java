package com.devhouse.core.services.cellchurch;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.response.CellChurchResponse;
import com.devhouse.core.model.validator.CellChurchModelValidator;
import com.devhouse.core.ports.inbound.cellchurch.UpdateCellChurchInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;

import java.time.Instant;

public class UpdateCellChurchService implements UpdateCellChurchInboundPort {

    private final CellChurchRepository cellChurchRepository;
    private final CellChurchModelValidator validator = new CellChurchModelValidator();

    public UpdateCellChurchService(CellChurchRepository cellChurchRepository) {
        this.cellChurchRepository = cellChurchRepository;
    }

    @Override
    public CellChurchResponse execute(CellChurch cellChurch) {
        validator.validate(cellChurch);
        CellChurch existing = cellChurchRepository.findById(cellChurch.getId())
                .orElseThrow(() -> new DomainException("CellChurch not found: " + cellChurch.getId()));
        CellChurch toUpdate = new CellChurch(
                cellChurch.getId(),
                cellChurch.getVersion(),
                cellChurch.getCellProfile(),
                cellChurch.getAddress(),
                cellChurch.getName(),
                cellChurch.getWeekday(),
                cellChurch.getHour(),
                existing.getCreatedDate(),
                Instant.now()
        );
        CellChurch updated = cellChurchRepository.update(toUpdate);
        return updated.toResponse();
    }
}
