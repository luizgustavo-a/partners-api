package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;

public class UpdatePartner {

    private final PartnerRepository partnerRepository;

    public UpdatePartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Partner updatePartner (Partner partner) {
        return partnerRepository.updatePartner(partner);
    }

}
