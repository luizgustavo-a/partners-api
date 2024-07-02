package ldelivery.api_partners.infra.gateway;

import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.domain.geolocation.Address;
import ldelivery.api_partners.domain.geolocation.CoverageArea;
import ldelivery.api_partners.infra.persistence.PartnerEntity;
import ldelivery.api_partners.infra.persistence.PartnerRepositoryPersistence;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClosestPartnerCalculator {

    private final PartnerRepositoryPersistence partnerRepository;

    public ClosestPartnerCalculator(PartnerRepositoryPersistence partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Partner calculateClosestPartner(Double latitude, Double longitude) {
        Point coordinates = GeoJsonConverter.toPoint(latitude, longitude);

        Optional<List<PartnerEntity>> insideCoverageArea = Optional.of(partnerRepository.findAll().stream()
                .filter(p -> containsCoordinate(p.getCoverageArea().getCoverageArea(), coordinates))
                .collect(Collectors.toList()));

        if(!insideCoverageArea.get().isEmpty()) {
            int n = insideCoverageArea.get().size();
            int index = 0;
            double closestDistance = 0;

            for (int i = 0; i < n; i++) {
                double distance = calculateDistance(insideCoverageArea.get().get(i).getAddress().getAdress(),
                        coordinates);

                if (i == 0) {
                    closestDistance = distance;
                }

                if (distance - closestDistance < 0) {
                    closestDistance = distance;
                    index = i;
                }
            }

            var partnerFound = insideCoverageArea.get().get(index);
            return new Partner(partnerFound.getId(), partnerFound.getTradingName(), partnerFound.getOwnerName(),
                    partnerFound.getDocument(),
                    new CoverageArea("MultiPolygon", partnerFound.getCoverageArea().getCoverageArea()),
                    new Address("Point", partnerFound.getAddress().getAdress()));
        }

        throw new IllegalArgumentException("No partner found close to the coordinates informed");
    }

    private Double calculateDistance (Point coordinatesBefore, Point coordinatesAfter){
        return Math.pow((coordinatesBefore.getX() - coordinatesAfter.getX()), 2) +
                Math.pow(coordinatesBefore.getY() - coordinatesAfter.getY(), 2);
    }

    private Boolean containsCoordinate (MultiPolygon polygon, Point coordinate) {
        return polygon.contains(coordinate);
    }

}

