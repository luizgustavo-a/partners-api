package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;
import ldelivery.api_partners.domain.entities.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class LoadPartner {

    private final PartnerRepository partnerRepository;

    public LoadPartner (PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Partner loadPartnerById(Long id) {
        return partnerRepository.loadPartnerById(id);
    }

    public Page<Partner> loadAllPartners(Pageable pageable) {
        return this.partnerRepository.loadAllPartners(pageable);
    }

}
