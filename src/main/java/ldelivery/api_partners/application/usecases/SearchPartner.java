package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SearchPartner {

    private final PartnerRepository partnerRepository;

    public SearchPartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;;
    }

    public Partner searchPartner(Double latitude, Double longitude) {
        return partnerRepository.searchClosestPartner(latitude, longitude);
    }

    public Page<Partner> searchPartnersInAddress(Double latitude, Double longitude, Pageable pageable){
        return partnerRepository.searchPartnersInAddress(latitude, longitude, pageable);
    }
}
