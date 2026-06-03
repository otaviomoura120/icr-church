package com.devhouse.core.ports.inbound;

import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;

public interface CreateFamilyInboundPort {
    FamilyResponse execute(Family family);
}
