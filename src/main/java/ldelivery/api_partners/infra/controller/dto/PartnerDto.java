package ldelivery.api_partners.infra.controller.dto;

public record PartnerDto(
        Long id,
        String tradingName,
        String ownerName,
        String document,
        CoverageAreaDto coverageArea,
        AddressDto address
) {
}
