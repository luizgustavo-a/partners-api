package ldelivery.api_partners.application.usecases;

import ldelivery.api_partners.application.gateway.PartnerRepository;

public class DeletePartner {

    private final PartnerRepository partnerRepository;

    public DeletePartner(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void deletePartner (Long id) {
        partnerRepository.deletePartner(id);
    }
}
