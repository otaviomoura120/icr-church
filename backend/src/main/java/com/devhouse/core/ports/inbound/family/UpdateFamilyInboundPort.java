package com.devhouse.core.ports.inbound.family;

import com.devhouse.core.model.Family;
import com.devhouse.core.model.response.FamilyResponse;

public interface UpdateFamilyInboundPort {
    FamilyResponse execute(Family family);
}
