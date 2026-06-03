package com.devhouse.core.services.cellchurch;

import com.devhouse.core.ports.inbound.cellchurch.DeleteCellChurchInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;

public class DeleteCellChurchService implements DeleteCellChurchInboundPort {

    private final CellChurchRepository cellChurchRepository;

    public DeleteCellChurchService(CellChurchRepository cellChurchRepository) {
        this.cellChurchRepository = cellChurchRepository;
    }

    @Override
    public void execute(Long id) {
        cellChurchRepository.delete(id);
    }
}
