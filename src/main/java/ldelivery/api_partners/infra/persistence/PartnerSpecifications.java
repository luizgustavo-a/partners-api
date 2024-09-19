package ldelivery.api_partners.infra.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;

public class PartnerSpecifications {

    public static Specification<PartnerEntity> containsCoordinate(Point point) {
        return ((Root<PartnerEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.isTrue(
                criteriaBuilder.function("ST_Contains", Boolean.class,
                        root.get("coverageArea").get("coverageArea"),
                        criteriaBuilder.literal(point))
        ));
    }

    public static Specification<PartnerEntity> closestPartner(Point point) {
        return (Root<PartnerEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(
                    criteriaBuilder.function("ST_Distance", Double.class,
                            root.get("address").get("address"),
                            criteriaBuilder.literal(point))
            ));
            return criteriaBuilder.conjunction();
        };
    }
}
