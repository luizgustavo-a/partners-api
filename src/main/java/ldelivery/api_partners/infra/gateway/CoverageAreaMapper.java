package ldelivery.api_partners.infra.gateway;

import ldelivery.api_partners.domain.geolocation.CoverageArea;
import ldelivery.api_partners.infra.controller.dto.CoverageAreaDto;
import ldelivery.api_partners.infra.persistence.CoverageAreaEntity;

public class CoverageAreaMapper {

    CoverageAreaDto toDto(CoverageArea coverageArea) {
        return new CoverageAreaDto(coverageArea.getType(),
                GeoJsonConverter.toCoordinates(coverageArea.getCoverageArea()));
    }

    CoverageArea toDomain (CoverageAreaDto coverageAreaEntity) {
        return new CoverageArea(coverageAreaEntity.type(),
                GeoJsonConverter.toMultiPolygon(coverageAreaEntity.coordinates()));
    }

    CoverageArea toDomain (CoverageAreaEntity coverageAreaEntity) {
        return new CoverageArea("MultiPolygon", coverageAreaEntity.getCoverageArea());
    }

    public CoverageAreaEntity toEntity (CoverageArea coverageArea) {
        return new CoverageAreaEntity(coverageArea.getCoverageArea());
    }

}
