package luiz.api_partners.application.usecases;

import luiz.api_partners.application.gateway.PartnerRepository;
import luiz.api_partners.domain.entities.partner.Partner;

public class CreatePartner {

    private final PartnerRepository partnerRepository;

    public CreatePartner (PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Partner createPartner(Partner partner) {
        return partnerRepository.createPartner(partner);
    }

}
