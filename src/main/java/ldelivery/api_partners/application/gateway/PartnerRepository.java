package ldelivery.api_partners.application.gateway;

import ldelivery.api_partners.domain.entities.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerRepository {

    Partner createPartner(Partner partner);

    Partner loadPartnerById(Long id);

    Page<Partner> loadAllPartners(Pageable pageable);

    Partner searchPartner(Double latitude, Double longitude);

    void updatePartner(Partner partner);

    void deletePartner(Long id);

}
