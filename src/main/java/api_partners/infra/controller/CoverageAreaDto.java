package api_partners.infra.controller;

import java.util.List;

public record CoverageAreaDto(
        String type,
        List<List<List<List<Double>>>> coordinates
) {
}
