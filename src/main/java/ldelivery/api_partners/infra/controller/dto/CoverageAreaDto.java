package ldelivery.api_partners.infra.controller.dto;

import java.util.List;

public record CoverageAreaDto(
        String type,
        List<List<List<List<Double>>>> coordinates
) {
}
