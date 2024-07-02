package ldelivery.api_partners.infra.gateway;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.infra.persistence.PartnerEntity;
import ldelivery.api_partners.infra.persistence.PartnerRepositoryPersistence;
import jakarta.persistence.EntityNotFoundException;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class PartnerRepositoryJpa implements PartnerRepository {

    private final PartnerRepositoryPersistence repository;
    private final PartnerMapper mapper;

    public PartnerRepositoryJpa(PartnerRepositoryPersistence repository, PartnerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Partner createPartner(Partner partner) {
        PartnerEntity entity = mapper.toEntity(partner);
        boolean alreadyRegistered = repository.existsByDocument(partner.getDocument());
        if (alreadyRegistered) {
            throw new IllegalArgumentException("Document already attached to another partner.");
        }
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Partner loadPartnerById(Long id) {
        var partnerEntity = repository.findByIdAndActiveTrue(id);
        Optional<Partner> partner = Optional.ofNullable(mapper.toDomain(partnerEntity.get()));
        return partner.orElseThrow(() -> new NoSuchElementException("Partner not found."));
    }

    @Override
    public Page<Partner> loadAllPartners(@PageableDefault Pageable pageable) {
        return repository.findAllByActiveTrue(pageable).map(mapper::toDomain);
    }

    @Override
    public List<Partner> searchPartnersInAddress(Double latitude, Double longitude) {
        ClosestPartnerCalculator calculator = new ClosestPartnerCalculator(repository);
        Point coordinates = GeoJsonConverter.toPoint(latitude, longitude);
        return repository.findAllByActiveTrue()
                .stream()
                .filter(p -> calculator.containsCoordinate(p.getCoverageArea().getCoverageArea(), coordinates))
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Partner searchPartner(Double latitude, Double longitude) {
        ClosestPartnerCalculator calculator = new ClosestPartnerCalculator(repository);
        Optional<Partner> closestPartner = Optional.ofNullable(calculator.calculateClosestPartner(latitude, longitude));
        return closestPartner.orElseThrow(() ->
                new EntityNotFoundException("No partner found close to the coordinated provided."));
    }

    @Override
    public void updatePartner(Partner partner) {
        var searchPartner = repository.findByIdAndActiveTrue(partner.getId()).get();
        searchPartner.updatePartner(partner);
    }

    @Override
    public void deletePartner(Long id) {
       var partner = repository.findByIdAndActiveTrue(id);
       partner.ifPresent(PartnerEntity::delete);
    }
}
