package api_partners.infra.gateway;

import api_partners.application.gateway.PartnerRepository;
import api_partners.domain.entities.partner.Partner;
import api_partners.infra.persistence.PartnerEntity;
import api_partners.infra.persistence.PartnerRepositoryPersistence;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
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
        var partnerEntity = repository.findById(id);
        Optional<Partner> partner = Optional.ofNullable(mapper.toDomain(partnerEntity.get()));
        return partner.orElseThrow(() -> new EntityNotFoundException("Partner not found."));
    }

    @Override
    public List<Partner> loadAllPartners() {
        return repository.findAll().stream()
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
}
