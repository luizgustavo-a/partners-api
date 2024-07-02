package ldelivery.api_partners.infra.persistence;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.MultiPolygon;

@Embeddable
public class CoverageAreaEntity {

    @NotNull
    private MultiPolygon coverageArea;

    public CoverageAreaEntity(MultiPolygon coverageArea) {
        this.coverageArea = coverageArea;
    }

    public CoverageAreaEntity(){}

    public MultiPolygon getCoverageArea() {
        return coverageArea;
    }

}

