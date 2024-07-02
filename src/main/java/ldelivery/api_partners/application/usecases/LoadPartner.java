package luiz.api_partners.application.usecases;

import luiz.api_partners.application.gateway.PartnerRepository;
import luiz.api_partners.domain.entities.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
