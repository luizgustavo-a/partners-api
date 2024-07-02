package ldelivery.api_partners.infra.gateway;

import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.infra.controller.PartnerDto;
import ldelivery.api_partners.infra.controller.UpdatePartnerDto;
import ldelivery.api_partners.infra.persistence.PartnerEntity;

public class PartnerMapper {

    private final AddressMapper addressMapper = new AddressMapper();
    private final CoverageAreaMapper coverageAreaMapper = new CoverageAreaMapper();

    public PartnerDto toDto (Partner partner) {
        return new PartnerDto(partner.getId(), partner.getTradingName(), partner.getOwnerName(),
                partner.getDocument(),
                coverageAreaMapper.toDto(partner.getCoverageArea()),
                addressMapper.toDto(partner.getAddress()));
    }

    public Partner toDomain (PartnerDto dto) {
        return new Partner(dto.id(), dto.tradingName(), dto.ownerName(), dto.document(),
                coverageAreaMapper.toDomain(dto.coverageArea()),
                addressMapper.toDomain(dto.address()));
    }

    public Partner toDomain (PartnerEntity entity) {
        return new Partner(entity.getId(), entity.getTradingName(), entity.getOwnerName(),
                entity.getDocument(), coverageAreaMapper.toDomain(entity.getCoverageArea()),
                addressMapper.toDomain(entity.getAddress()));
    }

    public Partner toDomain (UpdatePartnerDto dto) {
        return new Partner(dto.id(), dto.tradingName(), dto.ownerName(), null,
                coverageAreaMapper.toDomain(dto.coverageArea()),
                addressMapper.toDomain(dto.address()));
    }

    public PartnerEntity toEntity (Partner partner) {
        return new PartnerEntity(partner.getId(), partner.getTradingName(), partner.getOwnerName(),
                partner.getDocument(),
                coverageAreaMapper.toEntity(partner.getCoverageArea()),
                addressMapper.toEntity(partner.getAddress()));
    }

}
