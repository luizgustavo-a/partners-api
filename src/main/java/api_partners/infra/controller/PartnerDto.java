package api_partners.infra.controller;

public record PartnerDto(
        Long id,
        String tradingName,
        String ownerName,
        String document,
        CoverageAreaDto coverageArea,
        AddressDto address
) {
}
