package api_partners.domain.geolocation;

import org.locationtech.jts.geom.MultiPolygon;

public class CoverageArea {
    private String type;
    private MultiPolygon coverageArea;

    public CoverageArea(String type, MultiPolygon coverageArea) {
        this.type = type;
        this.coverageArea = coverageArea;
    }

    public String getType() {
        return type;
    }

    public MultiPolygon getCoverageArea() {
        return coverageArea;
    }

}