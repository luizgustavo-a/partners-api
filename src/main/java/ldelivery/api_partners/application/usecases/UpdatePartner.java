package luiz.api_partners.application.usecases;

import luiz.api_partners.application.gateway.PartnerRepository;
import luiz.api_partners.domain.entities.partner.Partner;

public class UpdatePartner {

    private final PartnerRepository partnerRepository;

    public UpdatePartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void updatePartner (Partner partner) {
        partnerRepository.updatePartner(partner);
    }

}
