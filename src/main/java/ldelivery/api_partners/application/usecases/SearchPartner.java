package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SearchPartner {

    private final PartnerRepository partnerRepository;

    public SearchPartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;;
    }

    public Partner searchPartner(Double latitude, Double longitude) {
        return partnerRepository.searchPartner(latitude, longitude);
    }

    public List<Partner> searchPartnersInAddress(Double latitude, Double longitude){
        return partnerRepository.searchPartnersInAddress(latitude, longitude);
    }
}
