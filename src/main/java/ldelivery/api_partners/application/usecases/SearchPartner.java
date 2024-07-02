package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;

public class SearchPartner {

    private final PartnerRepository partnerRepository;

    public SearchPartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;;
    }

    public Partner searchPartner(Double latitude, Double longitude) {
        return partnerRepository.searchPartner(latitude, longitude);
    }
}
