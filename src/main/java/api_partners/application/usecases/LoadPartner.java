package api_partners.application.usecases;

import api_partners.application.gateway.PartnerRepository;
import api_partners.domain.entities.partner.Partner;

import java.util.List;

public class LoadPartner {

    private final PartnerRepository partnerRepository;

    public LoadPartner (PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Partner loadPartnerById(Long id) {
        return partnerRepository.loadPartnerById(id);
    }

    public List<Partner> loadAllPartners() {
        return this.partnerRepository.loadAllPartners();
    }

}
