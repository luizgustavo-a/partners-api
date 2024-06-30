package api_partners.application.gateway;

import api_partners.domain.entities.partner.Partner;

import java.util.List;

public interface PartnerRepository {

    Partner createPartner(Partner partner);

    Partner loadPartnerById(Long id);

    List<Partner> loadAllPartners();

    Partner searchPartner(Double latitude, Double longitude);

}
