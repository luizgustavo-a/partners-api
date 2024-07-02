package luiz.api_partners.infra.gateway;

import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonConverter {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static MultiPolygon toMultiPolygon(List<List<List<List<Double>>>> coordinates) {
        Polygon[] polygons = coordinates.stream()
                .map(GeoJsonConverter::toPolygon)
                .toArray(Polygon[]::new);
        return geometryFactory.createMultiPolygon(polygons);
    }

    private static Polygon toPolygon(List<List<List<Double>>> polygonCoords) {
        LinearRing linearRing = toLinearRing(polygonCoords.get(0));
        LinearRing[] linearRings = polygonCoords.subList(1, polygonCoords.size()).stream()
                .map(GeoJsonConverter::toLinearRing)
                .toArray(LinearRing[]::new);
        return geometryFactory.createPolygon(linearRing, linearRings);
    }

    private static LinearRing toLinearRing(List<List<Double>> ringCoords) {
        Coordinate[] coordinates = ringCoords.stream()
                .map(c -> new Coordinate(c.get(0), c.get(1)))
                .toArray(Coordinate[]::new);
        return geometryFactory.createLinearRing(coordinates);
    }

    public static Point toPoint(List<Double> coordinates) {
        return geometryFactory.createPoint(new Coordinate(coordinates.get(0), coordinates.get(1)));
    }

    public static Point toPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(latitude, longitude));
    }

    public static List<List<List<List<Double>>>> toCoordinates(MultiPolygon multiPolygon) {
        List<List<List<List<Double>>>> coordinates = new ArrayList<>();
        for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
            coordinates.add(toCoordinates(polygon));
        }
        return coordinates;
    }

    private static List<List<List<Double>>> toCoordinates(Polygon polygon) {
        List<List<List<Double>>> coordinates = new ArrayList<>();
        coordinates.add(toCoordinates((LinearRing) polygon.getExteriorRing()));
        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            coordinates.add(toCoordinates((LinearRing) polygon.getInteriorRingN(i)));
        }
        return coordinates;
    }

    private static List<List<Double>> toCoordinates(LinearRing ring) {
        List<List<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : ring.getCoordinates()) {
            List<Double> point = new ArrayList<>();
            point.add(coordinate.getX());
            point.add(coordinate.getY());
            coordinates.add(point);
        }
        return coordinates;
    }

    public static List<Double> toCoordinates(Point point) {
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(point.getX());
        coordinates.add(point.getY());
        return coordinates;
    }
}
