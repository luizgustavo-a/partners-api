package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;

public class DeletePartner {

    private final PartnerRepository partnerRepository;

    public DeletePartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Partner deletePartner (Long id) {
        return partnerRepository.deletePartner(id);
    }
}
