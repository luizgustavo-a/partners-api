package ldelivery.api_partners.infra.gateway;

import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonConverter {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static MultiPolygon toMultiPolygon(List<List<List<List<Double>>>> coordinates) {
        Polygon[] polygons = new Polygon[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            polygons[i] = toPolygon(coordinates.get(i));
        }
        return geometryFactory.createMultiPolygon(polygons);
    }

    private static Polygon toPolygon(List<List<List<Double>>> polygonCoords) {
        LinearRing exteriorRing = toLinearRing(polygonCoords.get(0));
        LinearRing[] interiorRings = new LinearRing[polygonCoords.size() - 1];
        for (int i = 1; i < polygonCoords.size(); i++) {
            interiorRings[i - 1] = toLinearRing(polygonCoords.get(i));
        }
        return geometryFactory.createPolygon(exteriorRing, interiorRings);
    }

    private static LinearRing toLinearRing(List<List<Double>> ringCoords) {
        Coordinate[] coordinates = new Coordinate[ringCoords.size()];
        for (int i = 0; i < ringCoords.size(); i++) {
            coordinates[i] = new Coordinate(ringCoords.get(i).get(0), ringCoords.get(i).get(1));
        }
        return geometryFactory.createLinearRing(coordinates);
    }

    public static Point toPoint(List<Double> coordinates) {
        return geometryFactory.createPoint(new Coordinate(coordinates.get(0), coordinates.get(1)));
    }

    public static Point toPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(latitude, longitude));
    }

    public static List<List<List<List<Double>>>> toCoordinates(MultiPolygon multiPolygon) {
        List<List<List<List<Double>>>> coordinates = new ArrayList<>(multiPolygon.getNumGeometries());
        for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
            coordinates.add(toCoordinates(polygon));
        }
        return coordinates;
    }

    private static List<List<List<Double>>> toCoordinates(Polygon polygon) {
        List<List<List<Double>>> coordinates = new ArrayList<>(polygon.getNumInteriorRing() + 1);
        coordinates.add(toCoordinates((LinearRing) polygon.getExteriorRing()));
        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            coordinates.add(toCoordinates((LinearRing) polygon.getInteriorRingN(i)));
        }
        return coordinates;
    }

    private static List<List<Double>> toCoordinates(LinearRing ring) {
        Coordinate[] ringCoordinates = ring.getCoordinates();
        List<List<Double>> coordinates = new ArrayList<>(ringCoordinates.length);
        for (Coordinate coordinate : ringCoordinates) {
            List<Double> point = new ArrayList<>(2);
            point.add(coordinate.getX());
            point.add(coordinate.getY());
            coordinates.add(point);
        }
        return coordinates;
    }

    public static List<Double> toCoordinates(Point point) {
        List<Double> coordinates = new ArrayList<>(2);
        coordinates.add(point.getX());
        coordinates.add(point.getY());
        return coordinates;
    }
}
