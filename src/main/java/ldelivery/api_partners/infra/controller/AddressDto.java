package ldelivery.api_partners.infra.controller;

import java.util.List;

public record AddressDto(
        String type,
        List<Double> coordinates
) {
}
