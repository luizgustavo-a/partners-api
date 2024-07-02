package ldelivery.api_partners.infra.controller;

import jakarta.validation.constraints.NotNull;

public record UpdatePartnerDto (
        @NotNull
        Long id,
        String tradingName,
        String ownerName,
        CoverageAreaDto coverageArea,
        AddressDto address
) {
}
