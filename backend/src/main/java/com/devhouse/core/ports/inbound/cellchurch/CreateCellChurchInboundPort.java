package com.devhouse.core.ports.inbound.cellchurch;

import com.devhouse.core.model.CellChurch;
import com.devhouse.core.model.response.CellChurchResponse;

public interface CreateCellChurchInboundPort {
    CellChurchResponse execute(CellChurch cellChurch);
}
