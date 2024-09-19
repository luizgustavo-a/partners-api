package ldelivery.api_partners.infra.gateway;

import jakarta.persistence.EntityNotFoundException;
import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.infra.persistence.PartnerEntity;
import ldelivery.api_partners.infra.persistence.PartnerRepositoryPersistence;
import ldelivery.api_partners.infra.persistence.PartnerSpecifications;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    public Page<Partner> loadAllPartners(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Partner> searchPartnersInAddress(Double latitude, Double longitude, Pageable pageable) {
        Point coordinates = GeoJsonConverter.toPoint(latitude, longitude);

        var page = repository.findAll(PartnerSpecifications.containsCoordinate(coordinates), pageable);

        var filtered = page
                .stream()
                .filter(PartnerEntity::getActive)
                .map(mapper::toDomain)
                .toList();

        return new PageImpl<>(filtered, pageable, filtered.size());
    }

    @Override
    public Partner searchClosestPartner(Double latitude, Double longitude) {
        Point coordinates = GeoJsonConverter.toPoint(latitude, longitude);
        var page = repository.findAll(PartnerSpecifications.closestPartner(coordinates));

        Optional<Partner> closestPartner = Optional.ofNullable(page.get(0)).map(mapper::toDomain);

        return closestPartner.orElseThrow(() ->
                new EntityNotFoundException("No partner found close to the coordinated provided."));
    }

    @Override
    public Partner updatePartner(Partner partner) {
        var searchPartner = repository.findByIdAndActiveTrue(partner.getId()).get();
        searchPartner.updatePartner(partner);

        return mapper.toDomain(searchPartner);
    }

    @Override
    public Partner deletePartner(Long id) {
       var partner = repository.findByIdAndActiveTrue(id);
       partner.ifPresent(PartnerEntity::delete);

       return partner.map(mapper::toDomain).orElseThrow(() -> new NoSuchElementException("Partner not found."));
    }
}
