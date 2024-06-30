package api_partners.infra.controller;

import java.util.List;

public record MultiplePartnersDto(
        List<PartnerDto> pdvs
) {
}
